package stock.biz.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
	
	private static Properties props;
	
	private ConfigUtil(){}
	
	public static void init(Properties props) {
		if(ConfigUtil.props == null) {
			synchronized (ConfigUtil.class) {
				if(ConfigUtil.props == null) {
					ConfigUtil.props = props;
				}
			}
		}
	}
	
	public static void init(String fileClassPath) {
		// 生成输入流  
        InputStream ins=ConfigUtil.class.getResourceAsStream(fileClassPath);  
        // 生成properties对象  
        Properties props = new Properties();  
        try {  
        	props.load(ins);  
        } catch (Exception e) {  
            throw new RuntimeException(e);
        }
        init(props);
	}
	
	public static String getProperty(String key)
	{
        return getProperties().getProperty(key);
	}
	
	private static Properties getProperties(){
		if(props == null) {
			init("/config/config.properties");
		}
        return props;
	}
}
