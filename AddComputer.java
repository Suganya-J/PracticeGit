package RemotePCHelpdesk.Scripts.computerManagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
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

public class AddComputer extends TestBase{
	
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

		login = new Login(myBrowser, wait);
		hmPage = new HomePage(myBrowser, wait);
		computers = new ComputerManagement(myBrowser, wait);
		moduleName = AddComputer.class.getSimpleName();
	}
	// *** Make sure to keep your downloads directory empty before running this test**/
	@Test
	public void addComputerfromSuperAdmin() {
		// Login to the account by entering credentials.
		login.launchURL(repository.getProperty("rpcLoginPage"));
		login.enterText(repository.getProperty("txtbxEmail"), "ID", "suganya.panneerselvam+dec24@idrive.com");
		login.enterText(repository.getProperty("txtbxPWD"), "ID", "test90");
		login.click(repository.getProperty("btnLogin"), "ID");
		
		String compId = null;
		try {
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(repository.getProperty("TxtPlanType"))));
		    System.out.println("Plan type appeared");
		    computers.click(repository.getProperty("btnDeployRpc"), "ID");
		    //computers.click(repository.getProperty("btnDownloadRPChost"), "ID");		   
		    compId = computers.getTextorValue(repository.getProperty("computers.uniqueId"));
		    Runtime.getRuntime().exec("C:\\Users\\Suganya\\Downloads\\RemotePCHost_"+compId+".exe");
		    Thread.sleep(9000);
		    System.out.println("Host found");
		    String filepath = "D:\\Sikuli Screenshots\\";
		    Screen s = new Screen();
		    Pattern RPCSetup1 = new Pattern(filepath + "RPCSetup1.PNG");
		    Pattern RPCSetup2 = new Pattern(filepath + "RPCSetup2.PNG");
		    s.wait(RPCSetup1, 10);		    
		    s.click(RPCSetup1);
		    s.wait(RPCSetup2, 10);
		    s.click(RPCSetup2);
		}
		catch(Exception e) {
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
		//myBrowser.close();
		}	
}
