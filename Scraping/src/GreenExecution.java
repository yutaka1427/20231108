import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;    // この行を追加
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;  // この行を追加
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

	public class GreenExecution {
	public static void main(String[] args) {

			//GreenGetUrl.pageCl();
	       System.setProperty("webdriver.chrome.driver",
	        		"../chromedriver-win64\\chromedriver.exe");

//		System.setProperty("webdriver.chrome.driver", "C:\\pleiades\\2023-06\\workspace\\TestSelenium\\chromedriver-win64\\chromedriver.exe");

        // CSVファイルのパスを指定
        String csvFilePath = 
        		"../urls.csv";// CSVファイルのパス
       
        String csvOutputFilePath = "C:\\Users\\3030718\\Desktop\\output.csv"; // 結果を保存するCSVファイルのパス
try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvOutputFilePath), "Shift_JIS"))) {

	// WebDriverのインスタンスを作成
	WebDriver driver = new ChromeDriver();

			String line;
			while ((line = br.readLine()) != null) {
				// 各行からURLを取得
				String url = line.trim();

				try {
					// 取得したURLにアクセス
					driver.get(url);

					// h1タイトルを取得して表示
					WebElement h1TitleElement = driver.findElement(By.cssSelector("h1"));
					String pageTitle = h1TitleElement.getText();
					System.out.println("ページタイトル: " + pageTitle);

					
					// "勤務地"のテキストを取得
					String locationText = "";
					int divCount = driver.findElement(By.cssSelector("#__next > div.MuiBox-root.css-0 > div > div.MuiContainer-root.MuiContainer-maxWidthMd.MuiContainer-disableGutters.css-2hiy9a > div > div.css-njhkv6 > div.css-1hbyyce > div.css-78jar2"))
					    .findElements(By.tagName("div")).size();

					for (int i = 0; i < divCount; i++) {
					    int pCount = driver.findElement(By.cssSelector("#__next > div.MuiBox-root.css-0 > div > div.MuiContainer-root.MuiContainer-maxWidthMd.MuiContainer-disableGutters.css-2hiy9a > div > div.css-njhkv6 > div.css-1hbyyce > div.css-78jar2"))
					        .findElements(By.tagName("div")).get(i).findElements(By.tagName("p")).size();

					    for (int j = 0; j < pCount; j++) {
					        String searchText =  driver.findElement(By.cssSelector("#__next > div.MuiBox-root.css-0 > div > div.MuiContainer-root.MuiContainer-maxWidthMd.MuiContainer-disableGutters.css-2hiy9a > div > div.css-njhkv6 > div.css-1hbyyce > div.css-78jar2"))
					            .findElements(By.tagName("div")).get(i).findElements(By.tagName("p")).get(j).getText();

					        if (searchText.contains("勤務地詳細")) {
					        	locationText = searchText;
					            break;
					        }
					    }
					}
					
					
					String WorkingHoursTitle = "";
					int workingDivCount = driver.findElement(By.cssSelector("#__next > div.MuiBox-root.css-0 > div > div.MuiContainer-root.MuiContainer-maxWidthMd.MuiContainer-disableGutters.css-2hiy9a > div > div.css-njhkv6 > div.css-1hbyyce > div.css-78jar2 > div > p.MuiTypography-root.MuiTypography-body2.css-1r6p42g"))
					    .findElements(By.tagName("div")).size();

					for (int i = 0; i < workingDivCount; i++) {
					    int pCount = driver.findElement(By.cssSelector("#__next > div.MuiBox-root.css-0 > div > div.MuiContainer-root.MuiContainer-maxWidthMd.MuiContainer-disableGutters.css-2hiy9a > div > div.css-njhkv6 > div.css-1hbyyce > div.css-78jar2 > div > p.MuiTypography-root.MuiTypography-body2.css-1r6p42g"))
					        .findElements(By.tagName("div")).get(i).findElements(By.tagName("p")).size();
					
					    /////////ここ追加////////////
					    List<WebElement> pElements = driver.findElements(By.cssSelector("div.css-dbdle4 > p.css-1r6p42g"));
					    WebElement currentPEl = pElements.get(i);
					    /////////ここまで////////////
					
					    for (int j = 0; j < pCount - 1; j++) { // 最後の p 要素を除く
					        String searchText = driver.findElement(By.cssSelector("#__next > div.MuiBox-root.css-0 > div > div.MuiContainer-root.MuiContainer-maxWidthMd.MuiContainer-disableGutters.css-2hiy9a > div > div.css-njhkv6 > div.css-1hbyyce > div.css-78jar2 > div > p.MuiTypography-root.MuiTypography-body2.css-1r6p42g"))
					            .findElements(By.tagName("div")).get(i).findElements(By.tagName("p")).get(j).getText();

					        if (searchText.contains("勤務時間")) {
					        	
					            // "勤務時間"のテキストに一致するpタグを見つけたら、次の要素を取得
					            WebElement nextElement = currentPEl.findElement(By.xpath("following-sibling::*[1]"));
					            WorkingHoursTitle = nextElement.getText();
					            break;
					            
					        }
					    }
					}
					
					List<WebElement> elements = driver.findElements(By.cssSelector("#__next > div.MuiBox-root.css-0 > div > div.MuiContainer-root.MuiContainer-maxWidthMd.MuiContainer-disableGutters.css-2hiy9a > div > div.css-njhkv6 > div.css-1hbyyce > div.css-78jar2 > div > p.MuiTypography-root.MuiTypography-body2.css-1r6p42g"));
					for (WebElement element : elements) {
					    if (element.getText().contains("勤務時間")) {
					        WebElement nextElement = element.findElement(By.xpath("following-sibling::*[1]"));
					        WorkingHoursTitle = nextElement.getText();
					        break;
					    }
					}			
					
					System.out.println("勤務時間: " + WorkingHoursTitle);

                   

					// 結果をCSVファイルに書き込む
					bufferedWriter.write("\"" + pageTitle + "\",\"" + locationText +  "\",\""+ WorkingHoursTitle + "\",\"" + url + "\"\n");
//					bufferedWriter.write("\"" + pageTitle + url + "\"\n");
					//                   bufferedWriter.write("\"" + pageTitle + "\",\"" +  "\",\"" + url + "\"\n");
					bufferedWriter.flush();

				} finally {
				}
			}
				// WebDriverを終了しブラウザを閉じる
				driver.quit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

