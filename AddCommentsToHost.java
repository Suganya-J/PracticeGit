package RemotePCHelpdesk.Scripts.computerManagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import RemotePCHelpdesk.PageLibrary.computerManagement.ComputerManagement;
import RemotePCHelpdesk.PageLibrary.forgotPasswordPage.ForgotPassword;
import RemotePCHelpdesk.PageLibrary.homePage.HomePage;
import RemotePCHelpdesk.PageLibrary.invitedUserPage.InvitedUserPage;
import RemotePCHelpdesk.PageLibrary.loginPage.Login;
import RemotePCHelpdesk.Scripts.login.LoginToRPCEnterpriseAccount;
import RemotePCHelpdesk.TestBase.TestBase;

public class AddCommentsToHost extends TestBase{
	
	ComputerManagement computers = null;
	Login login = null;
	HomePage hmPage = null;

	@Parameters({ "excelFileStatus", "browser", "headlessMode" })
	@BeforeClass
	public void initializeEnvironment(@Optional("yes") String excelFileStatus, @Optional("chrome") String browser,
			@Optional("no") String headlessMode) throws FileNotFoundException, IOException {
		try {
			initializeProjectEnvironment(excelFileStatus, browser, headlessMode);
		} catch (FileNotFoundException fnfe) {
			System.out.println("Properties file could not be found");
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("Path for file is not found or incorrect.");
			ioe.printStackTrace();
		} catch (TimeoutException toe) {
			System.out.println("Wait creation failed");
		} catch (Exception e) {
			e.printStackTrace();
		}

		login = new Login(driver, wait);
		computers = new ComputerManagement(driver, wait);
		moduleName = AddCommentsToHost.class.getSimpleName();
	}
	
	@Test
	public void addingCommentsfromSuperAdmin() {
		//NewTestF
		login.launchURL(repository.getProperty("rpcLoginPage"));
		login.enterText(repository.getProperty("txtbxEmail"), "ID", "suganya.panneerselvam+dec24@idrive.com");
		login.enterText(repository.getProperty("txtbxPWD"), "ID", "test90");
		login.click(repository.getProperty("btnLogin"), "ID");
		
		try {
			driver.findElement(By.xpath("//*[@id=\"c_-1\"]/div[1]/a/span[1]")).click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(repository.getProperty("computers.StatusOnline1"))));

		} catch (Exception e) {
			System.out.println("Computer status is not online");
			moduleTestResult = "Fail";
			testResultComment = "\n" + e.getMessage();
			System.out.println(e.getMessage());
		}
		
		try {
		String Comment = "Test "+computers.generateNameusingDT();
	    computers.contextMenuClick("arguments[0].click();",repository.getProperty("btnContext2"));
	    computers.click(repository.getProperty("computers.Contextmenu.Comments"),"xpath");
	    Thread.sleep(2000);
	    computers.typeText(repository.getProperty("computers.Comments.InputTxt"),"xpath",Comment);
	    computers.click(repository.getProperty("computers.Comments.btnAdd"), "ID");
	    
	    if(repository.getProperty("computers.Comments.Description","xpath").contains(Comment))
	    	System.out.println("Comments added");
	    
	    computers.click(repository.getProperty("computers.Comments.btnClose"), "ID");
	    moduleTestResult = "Pass";    		
		testResultComment = "Comments added";
		
		} catch(Exception e) {
		System.out.println("Computer is not restarted successfully");
		moduleTestResult = "Fail";
		testResultComment = "\n" + e.getMessage();
		System.out.println(e.getMessage());
		}
	}
	@AfterClass
	public void closeEnvironment() {
		// Write the test results and then shut down the environment.
		System.out.println("\n===============");
		System.out.println("Module name: " + moduleName);
		System.out.println("Module test result: " + moduleTestResult);
		System.out.println("Test result comment: " + testResultComment);
	    //excelUtility.writeTestStatus(moduleName, moduleTestResult, testResultComment);
		//driver.close();
		}	
}
