package davatar.wu.stopdirtflowing;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import javassist.CtClass;
import javassist.CtMethod;

public class StopDirtFlowing implements WurmServerMod, PreInitable {    
    private static Logger logger = Logger.getLogger("AllowConcreteOnDirt");
    
    public static void logException(String msg, Throwable e) {
        if (logger != null) { logger.log(Level.SEVERE, msg, e); }
    }

    public static void logInfo(String msg) {
        if (logger != null) { logger.log(Level.INFO, msg); }
    }

    public String getVersion() {
    	return "0.1";
    }

    @Override
	public void preInit() {
		try { 
			CtClass ctClass = HookManager.getInstance().getClassPool().getCtClass("com.wurmonline.server.behaviours.MethodsItems");
			CtMethod ctMethod = ctClass.getDeclaredMethod("findDropTile");
			logInfo("Dirt can now be dropped everywhere, regardless of its slope.");
			// Return the position of the player
			ctMethod.setBody("{ return new com.wurmonline.server.Point($1, $2); }");
		} catch(Exception e) {
			logException("preInit: ", e);
			e.printStackTrace();
			throw new HookException(e);
		}
    }
}
