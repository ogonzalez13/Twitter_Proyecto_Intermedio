import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PublishTweet {
	
	static WebDriver driver; 
	WebDriverWait wait = new WebDriverWait(driver, 15);
	
	
	public static void main(String[] args) { 
		
		//configurar navegador
		configurarNavegador ();
		
		//Acceder a la cuenta 
		accederCuenta ();
		
		//Buscar Hashtag #1 
		byte hashtagNum = 1; // Número de Hashtag que buscas
		String hashTxt = buscarHashtag(hashtagNum);
		
		//Mandar twitter con el hashtag #1		
		String tuit= ("Hashtag 1 "+ hashTxt);
		
		enviarTweet (tuit);
		//Acceder al timeline
		entrarTimeline ();
		
		//Verificar Tweet
		verificarTweet (tuit);	
		
		//Regresar a Inicio
		inicioTwitter ();
		
		enviarTweet ("second tuit");
		
		//Cerrar navegador
		destroy ();
		
	}
	
	private static void configurarNavegador () {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://www.twitter.com");		
		
	}
	
	private static void accederCuenta (){
		WebElement user;		//xpath = //input[@placeholder ='Teléfono, correo electrónico o nombre de usuario']
		WebElement password;	//xpath = //input[@type='password']
		WebElement buttonLogin;	//xpath = //input[@type='submit']
		
		user = driver.findElement(By.xpath("//input[@placeholder ='Teléfono, correo electrónico o nombre de usuario']"));
		user.sendKeys("ogonzalez.tst@gmail.com");	 
		password = driver.findElement(By.xpath("//input[@type='password']"));	
		password.sendKeys("Test.1234");
		buttonLogin = driver.findElement(By.xpath("//input[@type='submit']"));
		buttonLogin.click();		
	}
	
	private static String buscarHashtag (byte hashTagnum) {
		WebElement hashtag; // xpath= //div[@class='trends-inner']//li[1]//a
		
		String path = "//div[@class='trends-inner']//li["+ hashTagnum +"]//a//span"; 
		hashtag = driver.findElement(By.xpath(path));
		System.out.println(hashtag.getText());
		String hashT = hashtag.getText();
		//hashtag.click();
		return hashT;
	}
	
	private static void enviarTweet (String tweet) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		
		WebElement textBox;	   //Id = tweet-box-home-timeline
		WebElement sendButton; //xpath = //* [contains (@class,'tweet-action EdgeButton EdgeButton--primary js-tweet-btn')]
		WebElement twit;       //div [@class ='js-tweet-text-container']
		
		textBox = driver.findElement(By.id("tweet-box-home-timeline"));
		textBox.click();
		textBox.sendKeys(tweet);
		sendButton = driver.findElement(By.xpath("//* [contains (@class,'tweet-action EdgeButton EdgeButton--primary js-tweet-btn')]"));
		sendButton.click();
		twit = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div [@class ='js-tweet-text-container'] /p /a [@data-query-source ='hashtag_click']")));
				
		

	}
	
	private static void entrarTimeline ( ) {
		WebElement avatar;         //id=user-dropdown-toggle
		WebElement optionProfile;  //xpath= //li [@class ='current-user'] //a
		
		avatar = driver.findElement(By.id("user-dropdown-toggle"));
		avatar.click();
		optionProfile = driver.findElement(By.xpath("//li [@class ='current-user'] //a"));
		optionProfile.click();
		
	}
	
	private static void verificarTweet (String tweet) {
		WebElement tuit ; //xpath = //div [@class = 'js-tweet-text-container'] //p [@class = 'TweetTextSize TweetTextSize--normal js-tweet-text tweet-text']
		
		tuit = driver.findElement(By.xpath("//div [@class = 'js-tweet-text-container'] //p [@class = 'TweetTextSize TweetTextSize--normal js-tweet-text tweet-text']"));
		System.out.println(tuit.getText());
		String verifyTuit = tuit.getText();
		System.out.println ("tweet original: " +tweet);
		System.out.println ("tweet recuperado: " +verifyTuit);
		
		if (tweet.equals(verifyTuit)) {
			System.out.println ("Tweet verificado");
		}  else {
			System.out.println ("No existe el tweet");
		}
				
	}
	
	private static void inicioTwitter () {
		WebElement botonInicio; //xpath = //a [@href='/']
		
		botonInicio = driver.findElement(By.xpath("//a [@href='/']"));
		botonInicio.click();
	}
	
	private static void destroy () {
		driver.close();
	}

}
