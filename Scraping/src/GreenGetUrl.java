import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

	public class GreenGetUrl {
    public static void main(String[] args) {
    	
//        public static void pageCl() {
    	
        // ChromeDriverのパスを設定
        System.setProperty("webdriver.chrome.driver",
        		"../chromedriver-win64\\chromedriver.exe");

        // WebDriverのインスタンスを作成
        WebDriver driver = new ChromeDriver();
//        String url = "https://www.green-japan.com/search_key?key=yc0n2c5lgpze85zyf23e&keyword=&page=1";
      
		String filePath = "../input.txt"; // テキストファイルのパス
        String url = null; // URLを格納する変数

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = bufferedReader.readLine();
            if (line != null) {
                url = line; // 読み込んだ行をURL変数に代入
                System.out.println("URL: " + url); // URL変数の内容を表示
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
 
        // 取得したURLにアクセス
        driver.get(url);
                
        FileWriter csvWriter;
        try {
        	csvWriter = new FileWriter("../urls.csv"); // CSVファイルを開く
            //csvWriter.append("URL"); // ヘッダーを追加
            //csvWriter.append("\n");

            while (true) {
                // すべてのリンク要素を取得
                List<WebElement> pageLinkElements = driver.findElements(By.cssSelector("a.js-search-result-box"));
                
                // ページ内に要素が表示されているか確認
                if (pageLinkElements.isEmpty()) {
                    break; // 要素が存在しない場合はループ終了
                }

                for (WebElement pageLinkElement : pageLinkElements) {
                    String linkUrl = pageLinkElement.getAttribute("href");
                    System.out.println("リンク先のURL: " + linkUrl);

                    // URLをCSVファイルに書き込む
                    csvWriter.append(linkUrl);
                    csvWriter.append("\n");
                }

                List<WebElement> nextPageLinks = driver.findElements(By.cssSelector("a.next_page[aria-label='next']"));
                
                // 次へのリンクが存在しなければループ終了
                if (nextPageLinks.isEmpty()) {
                    break;
                }

                // 次へのリンクをクリック
                nextPageLinks.get(0).click(); // 最初の次へのリンクをクリック
            }

            // CSVファイルを閉じる
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // WebDriverを終了
        driver.quit();
    }
}