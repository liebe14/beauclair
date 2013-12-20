package com.gss.gevee.cleint;

import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.gss.gevee.be.admin.entity.utilisateur.TabUsr;
import com.gss.gevee.be.admin.sisv.utilisateur.IRemoteUsr;
import com.gss.gevee.be.core.base.BaseLogger;
import com.gss.gevee.be.core.base.DateTools;
import com.gss.gevee.be.core.exception.GeveeAppException;

public class TestService {
	
	private static BaseLogger logger = BaseLogger.getLogger(TestService.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			Context context = new InitialContext();
 			
			IRemoteUsr svcUsr = (IRemoteUsr)context.lookup("gevee-z-ear/SvcoUsr/remote");
			TabUsr usr = new TabUsr();
			usr.setLibNom("Nanfack");
			usr.setLibPre("beauclair");
			usr.setCodUsr("liebes");
			usr.setBooSex("M");
			usr.setCodPwd("liebe14");
			usr.setLibAdr("Douala-Chococho");
			usr.setDatExpPwd(DateTools.formatDate(new Date()));
			usr.setDatEmbch(DateTools.formatDate(new Date()));
			svcUsr.creer(usr);
			
			TabUsr usrA =  svcUsr.authenticate("liebes", "liebe14");
			if(usrA!=null && usrA.getLibNom() !=null){
				logger.debug(" svcUsr.authenticate ok ok ok ok ok");
			}else{
				logger.debug(" svcUsr.authenticate no on on no no no no");
			}
			
			if(usrA.getPwdExpire() == true){
				logger.debug("pwd expiré");
			}
			
			usr = new TabUsr();
			usr.setLibNom("mego");
			usr.setLibPre("papix");
			usr.setCodUsr("root");
			usr.setBooSex("F");
			usr.setCodPwd("root");
			usr.setLibAdr("Yaoundé-calafatas");
			usr.setDatExpPwd(DateTools.formatDate(new Date()));
			usr.setDatEmbch(DateTools.formatDate(new Date()));
			svcUsr.creer(usr);
			
			List<TabUsr> list = svcUsr.rechercherTout(new TabUsr());
			logger.debug("la taille des users === " +list.size());
			
			TabUsr usr2 = list.get(0);
			logger.debug("usr2.name == "+ usr2.getLibNom());
			usr2.setLibNom("nameChange");
			svcUsr.modifier(usr2);
			
			TabUsr oneRslt = svcUsr.rechercher(new TabUsr(),"root");
			logger.debug("oneRslt.name ="+ oneRslt.getLibNom());
			
			svcUsr.supprimer(oneRslt);
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (GeveeAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
