package com.test.springboot.osgi.servletbridge.test;

import java.io.File;
import java.nio.file.Path;

import org.eclipse.equinox.servletbridge.FrameworkLauncher;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Hello world!
 *
 */
public class PackageAppTest 
{
	private static DesiredCapabilities getDesiredFirefoxCapabilities(String firefoxPath) {
		DesiredCapabilities capabilities = new FirefoxOptions()
			      .setProfile(new FirefoxProfile())
			      .addTo(DesiredCapabilities.firefox());

		capabilities.setCapability("marionette", true);
		if(null != firefoxPath){
			System.out.println("Firefox Path set to : " + firefoxPath);
			capabilities.setCapability("firefox_binary", firefoxPath);
		}
	  //capabilities.addPreference("browser.download.dir", text);// 配置响应参数：下载路径
	  //capabilities.addPreference("browser.download.folderList", 2);// 2为指定路径，0为默认路径
	    capabilities.setCapability("browser.download.manager.showWhenStarting", false);// 是否显示开始
	 // 禁止弹出保存框，value是文件格式
	    capabilities.setCapability("browser.helperApps.neverAsk.saveToDisk",
					"application/zip,application/msword,text/plain,application/vnd.ms-excel,text/csv,text/comma-separated-values,application/octet-stream,multipart/form-data;charset=utf-8"
				  + ",application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		capabilities.setCapability("browser.download.manager.showWhenStarting", false);
		capabilities.setCapability("browser.helperApps.alwaysAsk.force", false);
		capabilities.setCapability("dom.max_script_run_time", 0);
		     //不会弹出警告框
		capabilities.setCapability("browser.download.manager.alertOnEXEopen", false);
		return capabilities;
	}
	
	private static String genFirefoxPath(){
		//check if the 32bit firefox is installed
		Path firefoxPath = null;
		File x86Firefox = new File("C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		if(x86Firefox.exists()) {
			firefoxPath = x86Firefox.toPath();
		}

		//check if the 64bit firefox is installed
		File x64Firefox = new File("C:/Program Files/Mozilla Firefox/firefox.exe");
		if(x64Firefox.exists()) {
			firefoxPath = x64Firefox.toPath();
		}

		return null == firefoxPath ? "" : firefoxPath.toString();
	}
	
    public static void main( String[] args )
    {
    	String firefoxPath = genFirefoxPath();
    	DesiredCapabilities capabilities = getDesiredFirefoxCapabilities(firefoxPath);
    	FirefoxDriver FFDriver = new FirefoxDriver(capabilities);
    	FFDriver.navigate().to("http://cn.bing.com");
    	try {
			FFDriver.wait(4);
			FFDriver.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	FrameworkLauncher launcher = new FrameworkLauncher();
    	launcher.deploy();
    	launcher.start();
        System.out.println( "Hello World!" );
    }
}
