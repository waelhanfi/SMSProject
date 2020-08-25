/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTWappOperation;

/**
 *
 * @author wael
 */
import GTWapp.Login;
import GTWapp.AdminBean;
import au.com.bytecode.opencsv.CSVReader;

import controller.util.DataConnect;
import controller.util.SessionUtils;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.SQLException;
import javax.faces.application.FacesMessage;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.primefaces.PrimeFaces;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Row;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.shaded.commons.io.FilenameUtils;

public class DatabaseOperation {

    public static Statement stmtObj;

    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static ResultSet resultSetObj1;

    public static PreparedStatement pstmt;
    public static PreparedStatement pstmt1;
    public static PreparedStatement pstmt2;
    public static PreparedStatement pstmt3;
    public static PreparedStatement pstmt4;
    public static PreparedStatement pstmt5;
    public static PreparedStatement pstmt6;
    public static PreparedStatement pstmt7;
    public static PreparedStatement pstmt8;

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static Connection getConnection() {
        try {
            connObj = DataConnect.getConnection();

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return connObj;
    }

    public static ArrayList getAdminListFromDB() {

        Login l = new Login();

        ArrayList AdminList = new ArrayList();

        int IDUSER = 0;
        ResultSet idut;

        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT UTILISATEUR.ID_UTILISATEUR FROM UTILISATEUR WHERE UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            resultSetObj = stmtObj.executeQuery("SELECT *FROM  UTILISATEUR,  SOLDE,  ROLEU ,  PARENT WHERE  PARENT.ID_ADMIN=" + IDUSER + " AND   UTILISATEUR.ID_UTILISATEUR= PARENT.ID_CHILD  AND  UTILISATEUR.ID_UTILISATEUR= SOLDE.ID_UTILISATEUR AND  UTILISATEUR.ID_UTILISATEUR= ROLEU.ID_UTILISATEUR AND  ROLEU.ROLE='Admin'");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setNom(resultSetObj.getString("NOM"));
                AdminObj.setContactName(resultSetObj.getString("CONTACT_NAME"));
                AdminObj.setContactGsm(resultSetObj.getString("CONTACT_GSM"));
                AdminObj.setLogin(resultSetObj.getString("LOGIN"));
                AdminObj.setPwd(resultSetObj.getString("PWD"));
                AdminObj.setDateCreation(resultSetObj.getString("DATE_CREATION"));
                AdminObj.setActivated(resultSetObj.getString("ACTIVATED"));
                AdminObj.setIdSolde(resultSetObj.getInt("ID_SOLDE"));
                AdminObj.setSolde(resultSetObj.getString("SOLDE"));

                AdminList.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return AdminList;
    }

    public static ArrayList getUserListFromDB() {
        ArrayList AdminList = new ArrayList();

        int IDUSER = 0;
        ResultSet idut;

        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  ID_UTILISATEUR FROM  UTILISATEUR WHERE  LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }
            System.out.println("GTWappOperation.DatabaseOperation.getUserListFromDB()" + IDUSER);
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  UTILISATEUR,  SOLDE,  ROLEU,  PARENT WHERE  PARENT.ID_ADMIN=" + IDUSER + " AND   UTILISATEUR.ID_UTILISATEUR = PARENT.ID_CHILD  AND  UTILISATEUR.ID_UTILISATEUR = SOLDE.ID_UTILISATEUR AND  UTILISATEUR.ID_UTILISATEUR = ROLEU.ID_UTILISATEUR AND  ROLEU.ROLE ='User'");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setNom(resultSetObj.getString("NOM"));
                AdminObj.setContactName(resultSetObj.getString("CONTACT_NAME"));
                AdminObj.setContactGsm(resultSetObj.getString("CONTACT_GSM"));
                AdminObj.setLogin(resultSetObj.getString("LOGIN"));
                AdminObj.setPwd(resultSetObj.getString("PWD"));
                AdminObj.setDateCreation(resultSetObj.getString("DATE_CREATION"));
                AdminObj.setActivated(resultSetObj.getString("ACTIVATED"));
                AdminObj.setIdSolde(resultSetObj.getInt("ID_SOLDE"));
                AdminObj.setSolde(resultSetObj.getString("SOLDE"));

                AdminList.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return AdminList;
    }

    public static ArrayList BlackListList() {
        ArrayList BlackList = new ArrayList();

        int IDUSER = 0;
        ResultSet idut;

        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            resultSetObj = stmtObj.executeQuery("SELECT * FROM  BLACK_LIST WHERE  BLACK_LIST.ID_UTILISATEUR=" + IDUSER);
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setIdblacklist(resultSetObj.getInt("ID_BLACK_LIST"));
                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setGsmblacklist(resultSetObj.getString("GSM"));
                BlackList.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return BlackList;
    }

    public static ArrayList getUserProfilFromDB() {
        ArrayList AdminList = new ArrayList();

        int IDUSER = 0;
        ResultSet idut;

        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            resultSetObj = stmtObj.executeQuery("SELECT * FROM  UTILISATEUR, SOLDE, ROLEU, PARENT, ENTETE WHERE PARENT.ID_CHILD=" + IDUSER + " AND UTILISATEUR.ID_UTILISATEUR = PARENT.ID_CHILD  AND  UTILISATEUR.ID_UTILISATEUR = SOLDE.ID_UTILISATEUR AND  UTILISATEUR.ID_UTILISATEUR = ROLEU.ID_UTILISATEUR AND  UTILISATEUR.ID_UTILISATEUR = ENTETE.ID_UTILISATEUR AND  ROLEU.ROLE='User'");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setNom(resultSetObj.getString("NOM"));
                AdminObj.setContactName(resultSetObj.getString("CONTACT_NAME"));
                AdminObj.setContactGsm(resultSetObj.getString("CONTACT_GSM"));
                AdminObj.setLogin(resultSetObj.getString("LOGIN"));
                AdminObj.setPwd(resultSetObj.getString("PWD"));
                AdminObj.setDateCreation(resultSetObj.getString("DATE_CREATION"));
                AdminObj.setActivated(resultSetObj.getString("ACTIVATED"));
                AdminObj.setIdSolde(resultSetObj.getInt("ID_SOLDE"));
                AdminObj.setSolde(resultSetObj.getString("SOLDE"));
                AdminObj.setIdEntete(resultSetObj.getInt("ID_ENTETE"));
                AdminObj.setEntete(resultSetObj.getString("ENTETE"));

                AdminList.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return AdminList;
    }

    public static ArrayList getUserProfilNoFromDB() {
        ArrayList AdminList = new ArrayList();

        int IDUSER = 0;
        ResultSet idut;

        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            resultSetObj = stmtObj.executeQuery("SELECT * FROM  UTILISATEUR, SOLDE, ROLEU, PARENT, ENTETE WHERE PARENT.ID_CHILD=" + IDUSER + " AND UTILISATEUR.ID_UTILISATEUR= PARENT.ID_CHILD AND  ENTETE.ID_UTILISATEUR= UTILISATEUR.ID_UTILISATEUR AND UTILISATEUR.ID_UTILISATEUR = SOLDE.ID_UTILISATEUR AND  UTILISATEUR.ID_UTILISATEUR = ROLEU.ID_UTILISATEUR AND ROLEU.ROLE='User'");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setNom(resultSetObj.getString("NOM"));
                AdminObj.setContactName(resultSetObj.getString("CONTACT_NAME"));
                AdminObj.setContactGsm(resultSetObj.getString("CONTACT_GSM"));
                AdminObj.setLogin(resultSetObj.getString("LOGIN"));
                AdminObj.setPwd(resultSetObj.getString("PWD"));
                AdminObj.setDateCreation(resultSetObj.getString("DATE_CREATION"));
                AdminObj.setActivated(resultSetObj.getString("ACTIVATED"));
                AdminObj.setIdSolde(resultSetObj.getInt("ID_SOLDE"));
                AdminObj.setSolde(resultSetObj.getString("SOLDE"));

                AdminObj.setIdEntete(resultSetObj.getInt("ID_ENTETE"));
                AdminObj.setEntete(resultSetObj.getString("ENTETE"));

                AdminList.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return AdminList;
    }

    public static ArrayList showHist(int idUtilisateur) {

        ArrayList HistList = new ArrayList();
        try {

            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  HISTORIQUE WHERE HISTORIQUE.DATE_ALIMENTATION IS NOT NULL AND HISTORIQUE.ID_UTILISATEUR=" + idUtilisateur + " ORDER BY HISTORIQUE.DATE_ALIMENTATION ASC");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setIdHistorique(resultSetObj.getInt("ID_HISTORIQUE"));
                AdminObj.setSoldeInitial(resultSetObj.getInt("SOLDE_INITIAL"));
                AdminObj.setSoldeFinal(resultSetObj.getInt("SOLDE_FINAL"));
                AdminObj.setDateAlimentation(resultSetObj.getString("DATE_ALIMENTATION"));

                HistList.add(AdminObj);

            }
            //sessionMapObj.put("Hist", HistList);

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        return HistList;
    }

    public static ArrayList showEntet(int idUtilisateur) {

        ArrayList HistList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM HISTORIQUE_ENTETE WHERE HISTORIQUE_ENTETE.DATE_ENTETE IS NOT NULL AND HISTORIQUE_ENTETE.ID_UTILISATEUR=" + idUtilisateur + " ORDER BY  HISTORIQUE_ENTETE.DATE_ENTETE DESC");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setIdHistoriqueEntete(resultSetObj.getInt("ID_HISTORIQUE_ENTETE"));
                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setEnteteInitiale(resultSetObj.getString("ENTETE_INITIALE"));
                AdminObj.setEnteteFinale(resultSetObj.getString("ENTETE_FINALE"));
                AdminObj.setDateEntete(resultSetObj.getString("DATE_ENTETE"));

                HistList.add(AdminObj);

            }
            //sessionMapObj.put("Hist", HistList);

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return HistList;
    }

    public static ArrayList DiffCompList(int idUtilisateur) {

        ArrayList Diff = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT DIFFUSION_COMPAGNE.NOM_COMPAGNE, LISTE_GSM.NOM_LISTE, MESSAGE.NOM_MESSAGE, MESSAGE.MESSAGE, DIFFUSION_COMPAGNE.DATE_DIFFUSION_CREATION, DIFFUSION_COMPAGNE.DATE_ENVOIE, DIFFUSION_COMPAGNE.PLANIFIE, DIFFUSION_COMPAGNE.PARAMETRABLE FROM DIFFUSION_COMPAGNE, MESSAGE, LISTE_GSM WHERE DIFFUSION_COMPAGNE.ID_UTILISATEUR=" + idUtilisateur + " AND LISTE_GSM.ID_LISTE_GSM = DIFFUSION_COMPAGNE.ID_LIST AND MESSAGE.ID_MESSAGE = DIFFUSION_COMPAGNE.ID_MESSAGE");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();

                AdminObj.setNomCompagneDiffusion(resultSetObj.getString("NOM_COMPAGNE"));
                AdminObj.setNomListe(resultSetObj.getString("NOM_LISTE"));
                AdminObj.setNomMessage(resultSetObj.getString("NOM_MESSAGE"));
                AdminObj.setMessage(resultSetObj.getString("MESSAGE"));
                AdminObj.setDateDiffusionCreation(resultSetObj.getString("DATE_DIFFUSION_CREATION"));
                AdminObj.setDateEnvoie(resultSetObj.getString("DATE_ENVOIE"));
                AdminObj.setPlanifie(resultSetObj.getString("PLANIFIE"));
                AdminObj.setParametrable(resultSetObj.getString("PARAMETRABLE"));

                Diff.add(AdminObj);

            }
            //sessionMapObj.put("Hist", HistList);

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return Diff;
    }

    public static ArrayList ListMessagesFromDb(int idUtilisateur) {

        ArrayList MsgList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  MESSAGE WHERE  MESSAGE.ID_UTILISATEUR=" + idUtilisateur + " ORDER BY  MESSAGE.DATE_CREATION_MESSAGE DESC");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setIdMessage(resultSetObj.getInt("ID_MESSAGE"));
                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setNomMessage(resultSetObj.getString("NOM_MESSAGE"));
                AdminObj.setMessage(resultSetObj.getString("MESSAGE"));
                AdminObj.setDateCreationMessage(resultSetObj.getString("DATE_CREATION_MESSAGE"));
                AdminObj.setLongeur(resultSetObj.getInt("LONGEUR"));
                AdminObj.setNmbreSms(resultSetObj.getInt("NMBRE_SMS"));

                MsgList.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return MsgList;
    }

    public static ArrayList ListHistoriqueDiffOld(AdminBean newAdminObj) {
        ResultSet idut;
        int IDUSER = 0;
        String sta;
        ArrayList MsgList = new ArrayList();
        try {

            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            resultSetObj = stmtObj.executeQuery("SELECT GSM, MESSAGE, STATUS FROM  HISTORIQUE_DIFFUSION WHERE ID_DIFFUSION=" + newAdminObj.getNomCompagneDiffusion() + " AND ID_UTILISATEUR=" + IDUSER);
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setGsm(resultSetObj.getString("GSM"));
                AdminObj.setMessage(resultSetObj.getString("MESSAGE"));

                sta = resultSetObj.getString("STATUS");

                if (sta.equals("1")) {
                    sta = "Rejeté";
                }
                if (sta.equals("2")) {
                    sta = "Delivré";
                }
                if (sta.equals("3")) {
                    sta = "En attente";
                }

                AdminObj.setStatus(sta);

                MsgList.add(AdminObj);

            }

            connObj.close();

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        return MsgList;

    }

    public static void ListHistoriqueDiff(AdminBean newAdminObj) {
        AdminBean editRecord = null;
        ResultSet idut;
        int IDUSER = 0;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {

            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT UTILISATEUR.ID_UTILISATEUR FROM UTILISATEUR WHERE UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            resultSetObj = stmtObj.executeQuery("SELECT GSM, MESSAGE, STATUS FROM  HISTORIQUE_DIFFUSION WHERE ID_DIFFUSION=" + newAdminObj.getNomCompagneDiffusion() + " AND ID_UTILISATEUR=" + IDUSER);
            if (resultSetObj != null) {
                resultSetObj.next();

                editRecord = new AdminBean();

                editRecord.setGsm(resultSetObj.getString("GSM"));
                editRecord.setKey1(resultSetObj.getString("MESSAGE"));
                editRecord.setKey2(resultSetObj.getString("STATUS"));

            }
            sessionMapObj.put("DisplayListHist", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('detailslistDialog').show();");

    }

    public static ArrayList PieModel1RecordInDB(int id) {
        ResultSet idut;
        int IDUSER = 0;
        int Rejected = 0;
        int Delivred = 0;
        int Waiting = 0;

        ArrayList<Integer> num = new ArrayList<Integer>();
        ArrayList MsgList = new ArrayList();

        String sta = "";
        String nmComp = "";

        try {

            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            resultSetObj = stmtObj.executeQuery("SELECT STATUS FROM  HISTORIQUE_DIFFUSION WHERE ID_DIFFUSION='" + id + "' AND ID_UTILISATEUR=" + IDUSER);
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                // AdminObj.setStatus(resultSetObj.getString("STATUS"));          
                sta = resultSetObj.getString("STATUS");

                if (sta.equals("1")) {
                    Rejected = Rejected + 1;
                }
                if (sta.equals("2")) {
                    Delivred = Delivred + 1;
                }
                if (sta.equals("3")) {
                    Waiting = Waiting + 1;
                }

            }

            num.add(0, Rejected);
            num.add(1, Delivred);
            num.add(2, Waiting);

            FacesContext.getCurrentInstance().getExternalContext().redirect("PieModel.xhtml");
            connObj.close();

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        return num;

    }

    public static ArrayList ListHistoriqueAPI(AdminBean newAdminObj) {
        ResultSet idut;
        int IDUSER = 0;
        String sta;
        ArrayList MsgList = new ArrayList();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {

            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            String datedebut = dateFormat.format(newAdminObj.getDatetimedebut());
            String datedefin = dateFormat.format(newAdminObj.getDatetimfin());

            resultSetObj = stmtObj.executeQuery("SELECT GSM, MESSAGE, STATUS FROM  HISTORIQUE_DIFFUSION WHERE ID_DIFFUSION=0 AND ID_UTILISATEUR=" + IDUSER + " AND DATE_ENVOIE BETWEEN '" + datedebut + "' AND '" + datedefin + "'");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setGsm(resultSetObj.getString("GSM"));
                AdminObj.setMessage(resultSetObj.getString("MESSAGE"));

                sta = resultSetObj.getString("STATUS");

                if (sta.equals("1")) {
                    sta = "Rejeté";
                }
                if (sta.equals("2")) {
                    sta = "Delivré";
                }
                if (sta.equals("3")) {
                    sta = "En attente";
                }

                AdminObj.setStatus(sta);
                MsgList.add(AdminObj);

            }
            /* PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('apiidDialog').show();");
             */ connObj.close();

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        return MsgList;
    }

    public static Map<String, Integer> APIDashbordINDB(AdminBean newAdminObj) {
        ResultSet idut;
        int IDUSER = 0;
        int ct = 0;
        String date;
        ArrayList MsgList = new ArrayList();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Map<String, Integer> map = new LinkedHashMap<String, Integer>();

        try {

            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            String datedebut = dateFormat.format(newAdminObj.getDatetimedebut());
            String datedefin = dateFormat.format(newAdminObj.getDatetimfin());

            resultSetObj = stmtObj.executeQuery("SELECT DATE_ENVOIE, count(*) FROM  HISTORIQUE_DIFFUSION WHERE ID_DIFFUSION=0 AND ID_UTILISATEUR=" + IDUSER + " AND DATE_ENVOIE BETWEEN '" + datedebut + "' AND '" + datedefin + "' GROUP BY DATE_ENVOIE ORDER BY DATE_ENVOIE ASC");
            while (resultSetObj.next()) {

                //AdminBean AdminObj = new AdminBean();
                date = resultSetObj.getString("DATE_ENVOIE");
                ct = resultSetObj.getInt("COUNT(*)");

                map.put(date, ct);

            }

            FacesContext.getCurrentInstance().getExternalContext().redirect("chartModel.xhtml");

            connObj.close();

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        return map;

    }

    public static int CountDateEnvoie(int IDUSER, String date) {
        int ct = 0;
        try {

            resultSetObj1 = stmtObj.executeQuery("SELECT COUNT(*) FROM  HISTORIQUE_DIFFUSION WHERE ID_DIFFUSION=0 AND ID_UTILISATEUR=" + IDUSER + " AND DATE_ENVOIE='" + date + "'");
            resultSetObj1.next();
            ct = resultSetObj1.getInt("COUNT(*)");

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ct;
    }

    public static ArrayList ListGSMFromDb() {
        ResultSet idut;
        int IDUSER = 0;
        ArrayList GSMList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  LISTE_GSM WHERE  LISTE_GSM.ID_UTILISATEUR=" + IDUSER + " ORDER BY  LISTE_GSM.DATE_CREATION_LISTE DESC");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setIdListeGsm(resultSetObj.getInt("ID_LISTE_GSM"));
                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setNomListe(resultSetObj.getString("NOM_LISTE"));
                AdminObj.setDateCreationListe(resultSetObj.getString("DATE_CREATION_LISTE"));
                AdminObj.setParametrable(resultSetObj.getString("PARAMETRABLE"));
                GSMList.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return GSMList;
    }

    public static ArrayList ListCompagneFromDB() {
        ResultSet idut;
        int IDUSER = 0;
        ArrayList COMPList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT  DIFFUSION_COMPAGNE.NOM_COMPAGNE,  DIFFUSION_COMPAGNE.ID_DIFFUSION FROM  DIFFUSION_COMPAGNE WHERE  DIFFUSION_COMPAGNE.ID_UTILISATEUR=" + IDUSER + " AND  DIFFUSION_COMPAGNE.DATE_ENVOIE <= TO_CHAR (SYSDATE, 'YYYY/MM/DD HH24:MI:SS')");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();

                AdminObj.setNomCompagneDiffusion(resultSetObj.getString("NOM_COMPAGNE"));
                AdminObj.setIdDiffusion(resultSetObj.getInt("ID_DIFFUSION"));

                COMPList.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return COMPList;

    }

    public static ArrayList ListCompagneAPIFromDB() {
        ResultSet idut;
        int IDUSER = 0;
        ArrayList COMPList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT  DIFFUSION_COMPAGNE.NOM_COMPAGNE,  DIFFUSION_COMPAGNE.ID_DIFFUSION FROM  DIFFUSION_COMPAGNE WHERE  DIFFUSION_COMPAGNE.ID_UTILISATEUR=" + IDUSER + " AND  DIFFUSION_COMPAGNE.DATE_ENVOIE <= TO_CHAR (SYSDATE, 'YYYY/MM/DD HH24:MI:SS')");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();

                AdminObj.setNomCompagneDiffusion(resultSetObj.getString("NOM_COMPAGNE"));
                AdminObj.setIdDiffusion(resultSetObj.getInt("ID_DIFFUSION"));

                COMPList.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return COMPList;

    }

    public static ArrayList CompagneDiffusionListFromDb() {
        ResultSet idut;
        int IDUSER = 0;
        ArrayList COMPList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT  DIFFUSION_COMPAGNE.NOM_COMPAGNE,  DIFFUSION_COMPAGNE.ID_DIFFUSION FROM  DIFFUSION_COMPAGNE WHERE  DIFFUSION_COMPAGNE.ID_UTILISATEUR=" + IDUSER + " AND  DIFFUSION_COMPAGNE.DATE_ENVOIE <= TO_CHAR (SYSDATE, 'YYYY/MM/DD HH24:MI:SS')");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();

                AdminObj.setNomCompagneDiffusion(resultSetObj.getString("NOM_COMPAGNE"));
                AdminObj.setIdDiffusion(resultSetObj.getInt("ID_DIFFUSION"));

                COMPList.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return COMPList;
    }

    public static ArrayList DetailsListGSMFromDb(int idUtilisateur) {

        ArrayList GSMList = new ArrayList();
        ResultSet idutSession, idut;
        int listparam = 0;
        int IDUSER = 0;
        try {

            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  ID_UTILISATEUR FROM  UTILISATEUR WHERE  LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            idutSession = stmtObj.executeQuery("SELECT  LIST_PARAM FROM  OPTIONU WHERE  ID_UTILISATEUR=" + IDUSER);
            while (idutSession.next()) {
                listparam = idutSession.getInt("LIST_PARAM");
            }
            System.out.println("GTWappOperation.DatabaseOperation.DetailsListGSMFromDb()" + idUtilisateur);
            if (listparam == 1) {

                stmtObj = getConnection().createStatement();
                resultSetObj = stmtObj.executeQuery("SELECT * FROM  DETAIL_LIST_GSM WHERE ID_LIST=" + idUtilisateur);
                while (resultSetObj.next()) {
                    AdminBean AdminObj = new AdminBean();
                    AdminObj.setIdDetailListGsm(resultSetObj.getInt("ID_DETAIL_LIST_GSM"));
                    AdminObj.setIdList(resultSetObj.getInt("ID_LIST"));
                    AdminObj.setGsm(resultSetObj.getString("GSM"));
                    AdminObj.setKey1(resultSetObj.getString("KEY1"));
                    AdminObj.setKey2(resultSetObj.getString("KEY2"));
                    AdminObj.setKey3(resultSetObj.getString("KEY3"));
                    AdminObj.setKey4(resultSetObj.getString("KEY4"));
                    AdminObj.setKey5(resultSetObj.getString("KEY5"));
                    AdminObj.setKey6(resultSetObj.getString("KEY6"));
                    GSMList.add(AdminObj);

                }
            } else {
                resultSetObj = stmtObj.executeQuery("SELECT ID_DETAIL_LIST_GSM,ID_LIST,GSM FROM  DETAIL_LIST_GSM WHERE  ID_LIST=" + idUtilisateur);
                while (resultSetObj.next()) {
                    AdminBean AdminObj = new AdminBean();
                    AdminObj.setIdDetailListGsm(resultSetObj.getInt("ID_DETAIL_LIST_GSM"));
                    AdminObj.setIdList(resultSetObj.getInt("ID_LIST"));
                    AdminObj.setGsm(resultSetObj.getString("GSM"));
                    GSMList.add(AdminObj);

                }
            }
            connObj.close();

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return GSMList;
    }

    public static ArrayList DetailsListCompagneFromDb(int id) {
        ResultSet idut;
        int IDUSER = 0;
        String sta;
        ArrayList MsgList = new ArrayList();
        try {
            System.out.println("GTWappOperation.DatabaseOperation.DetailsListCompagneFromDb()" + id);
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  ID_UTILISATEUR FROM  UTILISATEUR WHERE  LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            System.out.println("GTWappOperation.DatabaseOperation.DetailsListCompagneFromDb()" + id);

            resultSetObj = stmtObj.executeQuery("SELECT  GSM, MESSAGE, STATUS FROM  HISTORIQUE_DIFFUSION WHERE ID_DIFFUSION=" + id + " AND ID_UTILISATEUR=" + IDUSER);
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();

                AdminObj.setGsm(resultSetObj.getString("GSM"));
                AdminObj.setMessage(resultSetObj.getString("MESSAGE"));

                sta = resultSetObj.getString("STATUS");

                if (sta.equals("1")) {
                    sta = "Rejeté";
                }
                if (sta.equals("2")) {
                    sta = "Delivré";
                }
                if (sta.equals("3")) {
                    sta = "En attente";
                }

                AdminObj.setStatus(sta);

                MsgList.add(AdminObj);

            }

            connObj.close();

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        return MsgList;
    }

    public static ArrayList DetailsListCompagneApiFromDb(int id) {
        ResultSet idut;
        int IDUSER = 0;
        String sta;
        ArrayList MsgList = new ArrayList();
        try {

            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            System.out.println("GTWappOperation.DatabaseOperation.DetailsListCompagneFromDb()" + id);

            resultSetObj = stmtObj.executeQuery("SELECT GSM, MESSAGE, STATUS FROM  HISTORIQUE_DIFFUSION WHERE ID_DIFFUSION=0 AND ID_UTILISATEUR=" + IDUSER);
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setGsm(resultSetObj.getString("GSM"));
                AdminObj.setMessage(resultSetObj.getString("MESSAGE"));

                sta = resultSetObj.getString("STATUS");

                if (sta.equals("1")) {
                    sta = "Rejeté";
                }
                if (sta.equals("2")) {
                    sta = "Delivré";
                }
                if (sta.equals("3")) {
                    sta = "En attente";
                }

                AdminObj.setStatus(sta);

                MsgList.add(AdminObj);

            }

            connObj.close();

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        return MsgList;
    }

    public static String saveAdminDetailsInDB(AdminBean newAdminObj) {
        int saveResult = 0;
        int saveResult1 = 0;
        int saveResult2 = 0;
        int saveResult3 = 0;
        int saveResult4 = 0;
        int IDUSER = 5555555;

        String navigationResult = "";
        ResultSet idut;
        ArrayList IDUTList = new ArrayList();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        int IDUSERSession = 0;
        ResultSet idutSession;

        String LOGIN = "";

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            idutSession = stmtObj.executeQuery("SELECT LOGIN FROM UTILISATEUR");
            while (idutSession.next()) {

                LOGIN = idutSession.getString("LOGIN");
                System.out.println("GTWappOperation.DatabaseOperation.saveAdminDetailsInDB()" + LOGIN + "/////" + newAdminObj.getLogin() + "/////" + LOGIN.equals(newAdminObj.getLogin()));

                // System.out.println("GTWappOperation.DatabaseOperation.saveAdminDetailsInDB()"+ (LOGIN == newAdminObj.getLogin()));
                if (LOGIN.equals(newAdminObj.getLogin())) {
                    FacesContext.getCurrentInstance().validationFailed();

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "login deja exist ", "login deja exist");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return null;
                }
            }
            pstmt = getConnection().prepareStatement("INSERT INTO SOLDE(ID_SOLDE , ID_UTILISATEUR , SOLDE ) VALUES(SOLDE_SEQ.NEXTVAL, ?, ?)");
            pstmt1 = getConnection().prepareStatement("INSERT INTO UTILISATEUR(ID_UTILISATEUR , NOM , CONTACT_NAME , CONTACT_GSM , LOGIN , PWD , DATE_CREATION , ACTIVATED) VALUES(UTILISATEUR_SEQ.NEXTVAL,?,?,?,?,?,?,?)");
            pstmt2 = getConnection().prepareStatement("INSERT INTO HISTORIQUE(ID_HISTORIQUE , ID_UTILISATEUR , SOLDE_INITIAL , SOLDE_FINAL , DATE_ALIMENTATION) VALUES(HISTORIQUE_SEQ.NEXTVAL, ?,?,?,?)");
            pstmt3 = getConnection().prepareStatement("INSERT INTO ROLEU(ID_ROLE , ID_UTILISATEUR , ROLE ) VALUES(ROLEU_SEQ.NEXTVAL, ?, 'Admin')");
            pstmt4 = getConnection().prepareStatement("INSERT INTO PARENT(ID_PARENT , ID_CHILD , ID_ADMIN ) VALUES(PARENT_SEQ.NEXTVAL,?," + IDUSERSession + ")");

            pstmt1.setString(1, newAdminObj.getNom());
            pstmt1.setString(2, newAdminObj.getContactName());
            pstmt1.setString(3, newAdminObj.getContactGsm());
            pstmt1.setString(4, newAdminObj.getLogin());
            pstmt1.setString(5, newAdminObj.getPwd());
            pstmt1.setString(6, dateFormat.format(date));
            pstmt1.setString(7, "True");

            saveResult1 = pstmt1.executeUpdate();

            stmtObj = getConnection().createStatement();
            idut = stmtObj.executeQuery("SELECT ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.ID_UTILISATEUR = ( SELECT MAX( UTILISATEUR.ID_UTILISATEUR) FROM  UTILISATEUR )");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            pstmt.setInt(1, IDUSER);
            pstmt.setString(2, newAdminObj.getSolde());

            pstmt2.setInt(1, IDUSER);
            pstmt2.setString(2, newAdminObj.getSolde());
            pstmt2.setString(3, newAdminObj.getSolde());
            pstmt2.setString(4, dateFormat.format(date));

            pstmt3.setInt(1, IDUSER);

            pstmt4.setInt(1, IDUSER);

            saveResult = pstmt.executeUpdate();
            saveResult2 = pstmt2.executeUpdate();
            saveResult3 = pstmt3.executeUpdate();
            saveResult4 = pstmt4.executeUpdate();

            new DatabaseOperation().hist_action(newAdminObj.getNom() + " Created by " + SessionUtils.getUserName());

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        if (saveResult != 0) {
            navigationResult = "ListAdmin.xhtml?faces-redirect=true";
        } else {

            navigationResult = "createAdmin.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }

    public static int saveGSMDetailsInDB(AdminBean newAdminObj) {
        System.out.println("GTWappOperation.DatabaseOperation.saveGSMDetailsInDB()22222222222");
        int saveResult = 0;

        String navigationResult = "";

        int IDUSERSession = 0;
        ResultSet idutSession;

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }
            pstmt = getConnection().prepareStatement("INSERT INTO black_list(id_black_list, id_utilisateur, gsm) VALUES(BLACK_LIST_SEQ.NEXTVAL," + IDUSERSession + ", ?)");

            pstmt.setString(1, newAdminObj.getGsmblacklist());

            saveResult = pstmt.executeUpdate();
            System.out.println("GTWappOperation.DatabaseOperation.saveGSMDetailsInDB()" + saveResult);

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return saveResult;
    }

    public static String ifParamInDB() {
        int saveResult = 0;

        String navigationResult = "";

        int IDUSERSession = 0;
        String ifparam = " ";
        ResultSet idutSession;
        ResultSet param;

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            param = stmtObj.executeQuery("SELECT  OPTIONU.LIST_PARAM FROM  OPTIONU WHERE  OPTIONU.ID_UTILISATEUR=" + IDUSERSession);
            while (param.next()) {
                ifparam = param.getString("LIST_PARAM");
            }
            if (ifparam.equals("1")) {
                return null;
            } else {
                return "none";
            }

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String ifdiffPlanifierInDB() {
        int saveResult = 0;

        String navigationResult = "";

        int IDUSERSession = 0;
        String ifdifpla = " ";
        ResultSet idutSession;
        ResultSet param;

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            param = stmtObj.executeQuery("SELECT  OPTIONU.DIFF_PLANIFIER FROM  OPTIONU WHERE  OPTIONU.ID_UTILISATEUR=" + IDUSERSession);
            while (param.next()) {
                ifdifpla = param.getString("DIFF_PLANIFIER");
            }
            if (ifdifpla.equals("1")) {
                return null;
            } else {
                return "none";
            }

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String ifAPIInDB() {
        int saveResult = 0;

        String navigationResult = "";

        int IDUSERSession = 0;
        String ifdAPI = " ";
        ResultSet idutSession;
        ResultSet param;

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            param = stmtObj.executeQuery("SELECT  OPTIONU.API FROM  OPTIONU WHERE  OPTIONU.ID_UTILISATEUR=" + IDUSERSession);
            while (param.next()) {
                ifdAPI = param.getString("API");
            }
            if (ifdAPI.equals("1")) {
                return null;
            } else {
                return "none";
            }

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String GetNomCompagneInDB(int id) {
        int saveResult = 0;

        String navigationResult = "";

        int IDUSERSession = 0;
        String ifdAPI = " ";
        ResultSet idutSession;
        ResultSet param;

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            param = stmtObj.executeQuery("SELECT NOM_COMPAGNE FROM  DIFFUSION_COMPAGNE WHERE ID_DIFFUSION=" + id + " AND ID_UTILISATEUR=" + IDUSERSession);
            while (param.next()) {
                return param.getString("NOM_COMPAGNE");
            }

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String ifmodifentetInDB() {
        int saveResult = 0;

        String navigationResult = "";

        int IDUSERSession = 0;
        String ifmdfen = " ";
        ResultSet idutSession;
        ResultSet param;

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            param = stmtObj.executeQuery("SELECT  OPTIONU.MODIF_ENTET FROM  OPTIONU WHERE  OPTIONU.ID_UTILISATEUR=" + IDUSERSession);
            while (param.next()) {
                ifmdfen = param.getString("MODIF_ENTET");
            }
            if (ifmdfen.equals("1")) {
                return null;
            } else {
                return "none";
            }

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String saveDiffCompInDB(AdminBean newAdminObj) {
        int saveResult = 0;
        int saveResult1 = 0;
        int saveResult2 = 0;
        int saveResult3 = 0;
        int saveResult4 = 0;
        int IDUSER = 5555555;

        String navigationResult = "";
        ResultSet idut;
        String msgDIFF = "";
        String gsmDIFF = "";
        String SMSF = " ";
        String k1 = " ";
        String k2 = " ";
        String k3 = " ";
        String k4 = " ";
        String k5 = " ";
        String k6 = " ";
        String value = " ";
        ArrayList IDUTList = new ArrayList();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        int IDUSERSession = 0;
        int ID_LISTE_GSM = 0;
        int ID_MESSAGE = 0;
        int IDDIFF = 0;
        int Sold = 0;
        int ct = 0;
        int countt = 0;
        int nbSMSF = 0;
        int newSolde = 0;
        String param;
        String ifparam = "";
        String ENTET = "";
        ResultSet paramt;
        ResultSet idutSession;
        ResultSet idutListMSG;
        ResultSet idutMSG;
        ResultSet idutENTET;
        ResultSet idutDIF;
        ResultSet msgDIF;
        ResultSet GSMDIF;
        ResultSet gtSolde;
        ResultSet ctlIST;
        ResultSet NBsms;
        int k = 1;
        ArrayList<Integer> WrongValue = new ArrayList<Integer>();
        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            pstmt = getConnection().prepareStatement("INSERT INTO DIFFUSION_COMPAGNE(ID_DIFFUSION, ID_UTILISATEUR, ID_MESSAGE, ID_LIST, DATE_DIFFUSION_CREATION, NOM_COMPAGNE, ENTETE, DATE_ENVOIE, PLANIFIE, PARAMETRABLE) VALUES (DIFFUSION_COMPAGNE_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, IDUSERSession);

            stmtObj = getConnection().createStatement();
            idutMSG = stmtObj.executeQuery("SELECT * FROM  MESSAGE WHERE  NOM_MESSAGE='" + newAdminObj.getNomMessage() + "'");
            while (idutMSG.next()) {
                ID_MESSAGE = idutMSG.getInt("ID_MESSAGE");
            }
            System.out.println("GTWappOperation.DatabaseOperation.saveDiffCompInDB() 11*" + ID_MESSAGE);

            pstmt.setInt(2, ID_MESSAGE);

            stmtObj = getConnection().createStatement();
            idutListMSG = stmtObj.executeQuery("SELECT * FROM  LISTE_GSM WHERE  NOM_LISTE='" + newAdminObj.getNomListe() + "'");
            while (idutListMSG.next()) {
                ID_LISTE_GSM = idutListMSG.getInt("ID_LISTE_GSM");
            }
            pstmt.setInt(3, ID_LISTE_GSM);
            System.out.println("GTWappOperation.DatabaseOperation.saveDiffCompInDB() 22*" + ID_LISTE_GSM);

            pstmt.setString(4, dateFormat.format(date));
            System.out.println("GTWappOperation.DatabaseOperation.saveDiffCompInDB() 33*" + dateFormat.format(date));

            pstmt.setString(5, newAdminObj.getNomCompagneDiffusion());
            System.out.println("GTWappOperation.DatabaseOperation.saveDiffCompInDB() 44*" + newAdminObj.getNomCompagneDiffusion());

            stmtObj = getConnection().createStatement();
            idutENTET = stmtObj.executeQuery("SELECT * FROM  ENTETE WHERE  ENTETE.ID_UTILISATEUR=" + IDUSERSession);
            while (idutENTET.next()) {
                ENTET = idutENTET.getString("ENTETE");
            }
            System.out.println("GTWappOperation.DatabaseOperation.saveDiffCompInDB() 55*" + ENTET);

            paramt = stmtObj.executeQuery("SELECT  OPTIONU.LIST_PARAM FROM  OPTIONU WHERE  OPTIONU.ID_UTILISATEUR=" + IDUSERSession);
            while (paramt.next()) {
                ifparam = paramt.getString("LIST_PARAM");
            }

            if (ifparam.equals("1")) {
                param = newAdminObj.getParametrable();
            } else {
                param = "false";
            }

            pstmt.setString(6, ENTET);
            pstmt.setString(7, dateFormat.format(date));
            pstmt.setString(8, "False");
            pstmt.setString(9, param);
            System.out.println("GTWappOperation.DatabaseOperation.saveDiffCompInDB() 66*" + param);

            if (newAdminObj.getParametrable() == "false") {

                stmtObj = getConnection().createStatement();
                gtSolde = stmtObj.executeQuery("SELECT  SOLDE FROM  SOLDE WHERE  ID_UTILISATEUR=" + IDUSERSession);
                while (gtSolde.next()) {
                    Sold = gtSolde.getInt("SOLDE");
                }

                stmtObj = getConnection().createStatement();
                ctlIST = stmtObj.executeQuery("SELECT COUNT(*) FROM  DETAIL_LIST_GSM WHERE  ID_LIST=" + ID_LISTE_GSM);
                while (ctlIST.next()) {
                    ct = ctlIST.getInt("COUNT(*)");
                }

                stmtObj = getConnection().createStatement();
                NBsms = stmtObj.executeQuery("SELECT  NMBRE_SMS FROM  MESSAGE WHERE  ID_MESSAGE=" + ID_MESSAGE);
                while (NBsms.next()) {
                    nbSMSF = NBsms.getInt("NMBRE_SMS");
                }

                if (Sold > ct * nbSMSF) {
                    newSolde = Sold - ct * nbSMSF;

                    pstmt6 = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE = " + newSolde + " WHERE  ID_UTILISATEUR = " + IDUSERSession);
                    saveResult4 = pstmt6.executeUpdate();

                    saveResult = pstmt.executeUpdate();

                    stmtObj = getConnection().createStatement();
                    idutDIF = stmtObj.executeQuery("SELECT  ID_LIST FROM (SELECT ID_LIST FROM DIFFUSION_COMPAGNE ORDER BY ID_DIFFUSION DESC) where rownum = 1");
                    while (idutDIF.next()) {

                        ID_LISTE_GSM = idutDIF.getInt("ID_LIST");

                    }
                    System.out.println("GTWappOperation.DatabaseOperation.saveDiffCompInDB() 77*" + ID_LISTE_GSM);

                    stmtObj = getConnection().createStatement();
                    GSMDIF = stmtObj.executeQuery("SELECT  DETAIL_LIST_GSM.GSM FROM  LISTE_GSM,  DETAIL_LIST_GSM WHERE  LISTE_GSM.ID_UTILISATEUR=" + IDUSERSession + " AND  LISTE_GSM.ID_LISTE_GSM= DETAIL_LIST_GSM.ID_LIST AND  LISTE_GSM.ID_LISTE_GSM=" + ID_LISTE_GSM + " AND  DETAIL_LIST_GSM.GSM NOT IN ( SELECT  BLACK_LIST.GSM FROM  BLACK_LIST WHERE  BLACK_LIST.ID_UTILISATEUR=" + IDUSERSession + ")");

                    while (GSMDIF.next()) {

                        stmtObj = getConnection().createStatement();
                        idutDIF = stmtObj.executeQuery("SELECT  ID_DIFFUSION, ID_MESSAGE, ID_LIST FROM (SELECT * FROM DIFFUSION_COMPAGNE ORDER BY ID_DIFFUSION DESC) where rownum = 1");
                        while (idutDIF.next()) {
                            IDDIFF = idutDIF.getInt("ID_DIFFUSION");
                            ID_MESSAGE = idutDIF.getInt("ID_MESSAGE");
                            ID_LISTE_GSM = idutDIF.getInt("ID_LIST");

                        }

                        stmtObj = getConnection().createStatement();
                        msgDIF = stmtObj.executeQuery("SELECT  MESSAGE FROM  MESSAGE WHERE  ID_MESSAGE=" + ID_MESSAGE);

                        while (msgDIF.next()) {
                            msgDIFF = msgDIF.getString("MESSAGE");
                        }
                        System.out.println("GTWappOperation.DatabaseOperation.saveDiffCompInDB() 88*" + msgDIFF);

                        gsmDIFF = GSMDIF.getString("GSM");
                        System.out.println("GTWappOperation.DatabaseOperation.saveDiffCompInDB() 99*" + gsmDIFF);

                        int numGsm = 0;
                        numGsm = Integer.parseInt(gsmDIFF);

                        System.out.println("GTWappOperation.DatabaseOperation.saveDiffCompInDB()" + IDDIFF + "//" + msgDIFF + "//" + gsmDIFF + "//" + ENTET + "//" + IDUSERSession + "//" + dateFormat.format(date));
                        pstmt5 = getConnection().prepareStatement("INSERT INTO DETAILS_DIFFUSION(ID_DETAILS, ID_DIFFUSION, MESSAGE, GSM, ENTETE, ID_UTILISATEUR, DATE_ENVOIE) VALUES(ID_DETAILS_SEQ.NEXTVAL, " + IDDIFF + ", '" + msgDIFF + "', " + numGsm + ", '" + ENTET + "', " + IDUSERSession + ", '" + dateFormat.format(date) + "')");
                        saveResult1 = pstmt5.executeUpdate();
                    }

                }

            } else {

                stmtObj = getConnection().createStatement();
                Statement stmtObj1 = getConnection().createStatement();
                Statement stmtObj2 = getConnection().createStatement();

                int SEUIL = 160;
                int SEUILAR = 70;
                int j = 1;

                Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");

                gtSolde = stmtObj.executeQuery("SELECT  SOLDE.SOLDE FROM  SOLDE WHERE  SOLDE.ID_UTILISATEUR=" + IDUSERSession);
                while (gtSolde.next()) {
                    Sold = gtSolde.getInt("SOLDE");
                }

                ctlIST = stmtObj1.executeQuery("SELECT GSM,KEY1,KEY2,KEY3,KEY4,KEY5,KEY6 FROM  DETAIL_LIST_GSM WHERE ID_LIST=" + ID_LISTE_GSM);
                while (ctlIST.next()) {

                    String gsm = ctlIST.getString("GSM");
                    k1 = ctlIST.getString("KEY1");
                    k2 = ctlIST.getString("KEY2");
                    k3 = ctlIST.getString("KEY3");
                    k4 = ctlIST.getString("KEY4");
                    k5 = ctlIST.getString("KEY5");
                    k6 = ctlIST.getString("KEY6");

                    NBsms = stmtObj.executeQuery("SELECT  MESSAGE FROM  MESSAGE WHERE  ID_MESSAGE=" + ID_MESSAGE);
                    while (NBsms.next()) {
                        value = NBsms.getString("MESSAGE");
                    }
                    value = value.replace("KEY1", k1);
                    value = value.replace("KEY2", k2);
                    value = value.replace("KEY3", k3);
                    value = value.replace("KEY4", k4);
                    value = value.replace("KEY5", k5);
                    value = value.replace("KEY6", k6);// replace space with hiphen
                    value = value.replace("'", "''");
                    Matcher matcher = pattern.matcher(value);
                    for (int i = 0; i < value.length(); i++) {
                        if (value.charAt(i) != ' ') {
                            countt++;
                        }
                    }

                    if (!matcher.matches()) {

                        while (countt > SEUILAR) {
                            SEUILAR = SEUILAR + 70;
                            j++;
                        }
                    }

                    if (matcher.matches()) {
                        while (countt > SEUIL) {
                            SEUIL = SEUIL + 160;
                            j++;
                        }
                    }
                    if (Sold > j) {
                        newSolde = Sold - j;

                        pstmt6 = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE = '" + newSolde + "' WHERE  ID_UTILISATEUR = " + IDUSERSession);
                        pstmt6.executeUpdate();
                        pstmt.executeUpdate();
                        idutDIF = stmtObj2.executeQuery("SELECT ID_DIFFUSION,ID_MESSAGE,ID_LIST FROM (SELECT * FROM DIFFUSION_COMPAGNE ORDER BY ID_DIFFUSION DESC) where rownum = 1");
                        while (idutDIF.next()) {
                            IDDIFF = idutDIF.getInt("ID_DIFFUSION");
                            ID_MESSAGE = idutDIF.getInt("ID_MESSAGE");
                            ID_LISTE_GSM = idutDIF.getInt("ID_LIST");
                        }
                        int numGsm = 0;
                        numGsm = Integer.parseInt(gsm);
                        System.out.println("8888888888888888888:::::::::::" + IDDIFF + "/" + value + "/" + numGsm + "/" + ENTET + "/" + IDUSERSession + "/" + dateFormat.format(date));
                        pstmt5 = getConnection().prepareStatement("INSERT INTO DETAILS_DIFFUSION(ID_DETAILS, ID_DIFFUSION, MESSAGE, GSM, ENTETE, ID_UTILISATEUR, DATE_ENVOIE) VALUES(ID_DETAILS_SEQ.NEXTVAL, " + IDDIFF + ", '" + value + "', " + numGsm + ", '" + ENTET + "', " + IDUSERSession + ", '" + dateFormat.format(date) + "')");
                        pstmt5.executeUpdate();

                    }
                }

            }
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        if (saveResult != 0) {
            navigationResult = "UserProfil.xhtml?faces-redirect=true";
        } else {
            navigationResult = "UserProfil.xhtml?faces-redirect=true";
        }
        new DatabaseOperation().hist_action("The user with the name of " + SessionUtils.getUserName() + " ID=" + IDUSERSession + " Add Compagne Instantané ");

        return navigationResult;
    }

    public static String saveDiffCompPlaInDB(AdminBean newAdminObj) {
        int saveResult = 0;
        int saveResult1 = 0;
        int saveResult2 = 0;
        int saveResult3 = 0;
        int saveResult4 = 0;
        int IDUSER = 5555555;

        String navigationResult = "";
        ResultSet idut;
        String msgDIFF = "";
        String gsmDIFF = "";
        String SMSF = " ";
        String k1 = " ";
        String k2 = " ";
        String k3 = " ";
        String k4 = " ";
        String k5 = " ";
        String k6 = " ";
        String value = " ";
        ArrayList IDUTList = new ArrayList();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        int IDUSERSession = 0;
        int ID_LISTE_GSM = 0;
        int ID_MESSAGE = 0;
        int IDDIFF = 0;
        int Sold = 0;
        int ct = 0;
        int countt = 0;
        int nbSMSF = 0;
        int newSolde = 0;

        String param;
        String ifparam = "";

        ResultSet paramt;
        String DE = "";
        String ENTET = "";
        ResultSet idutSession;
        ResultSet idutListMSG;
        ResultSet idutMSG;
        ResultSet idutENTET;
        ResultSet idutDIF;
        ResultSet msgDIF;
        ResultSet GSMDIF;
        ResultSet gtSolde;
        ResultSet ctlIST;
        ResultSet NBsms;

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  ID_UTILISATEUR FROM  UTILISATEUR WHERE  LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            pstmt = getConnection().prepareStatement("INSERT INTO DIFFUSION_COMPAGNE(ID_DIFFUSION, ID_UTILISATEUR, ID_MESSAGE, ID_LIST, DATE_DIFFUSION_CREATION, NOM_COMPAGNE, ENTETE, DATE_ENVOIE, PLANIFIE, PARAMETRABLE) VALUES(DIFFUSION_COMPAGNE_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, IDUSERSession);

            stmtObj = getConnection().createStatement();
            idutMSG = stmtObj.executeQuery("SELECT * FROM  MESSAGE WHERE  NOM_MESSAGE='" + newAdminObj.getNomMessage() + "'");
            while (idutMSG.next()) {
                ID_MESSAGE = idutMSG.getInt("ID_MESSAGE");
            }

            pstmt.setInt(2, ID_MESSAGE);

            stmtObj = getConnection().createStatement();
            idutListMSG = stmtObj.executeQuery("SELECT * FROM  LISTE_GSM WHERE  NOM_LISTE='" + newAdminObj.getNomListe() + "'");
            while (idutListMSG.next()) {
                ID_LISTE_GSM = idutListMSG.getInt("ID_LISTE_GSM");
            }

            String s = dateFormat.format(newAdminObj.getDate10());

            pstmt.setInt(3, ID_LISTE_GSM);

            pstmt.setString(4, dateFormat.format(date));

            pstmt.setString(5, newAdminObj.getNomCompagneDiffusion());

            stmtObj = getConnection().createStatement();
            idutENTET = stmtObj.executeQuery("SELECT * FROM  ENTETE WHERE  ID_UTILISATEUR=" + IDUSERSession);
            while (idutENTET.next()) {
                ENTET = idutENTET.getString("ENTETE");
            }

            paramt = stmtObj.executeQuery("SELECT  LIST_PARAM FROM  OPTIONU WHERE  ID_UTILISATEUR=" + IDUSERSession);
            while (paramt.next()) {
                ifparam = paramt.getString("LIST_PARAM");
            }

            if (ifparam.equals("1")) {
                param = newAdminObj.getParametrable();
            } else {
                param = "false";
            }

            pstmt.setString(6, ENTET);
            pstmt.setString(7, s);
            pstmt.setString(8, "True");
            pstmt.setString(9, param);

            if (newAdminObj.getParametrable() == "false") {

                stmtObj = getConnection().createStatement();
                gtSolde = stmtObj.executeQuery("SELECT  SOLDE FROM  SOLDE WHERE  ID_UTILISATEUR=" + IDUSERSession);
                while (gtSolde.next()) {
                    Sold = gtSolde.getInt("SOLDE");
                }

                stmtObj = getConnection().createStatement();
                ctlIST = stmtObj.executeQuery("SELECT COUNT(*) FROM  DETAIL_LIST_GSM WHERE  ID_LIST=" + ID_LISTE_GSM);
                while (ctlIST.next()) {
                    ct = ctlIST.getInt("COUNT(*)");
                }

                stmtObj = getConnection().createStatement();
                NBsms = stmtObj.executeQuery("SELECT  NMBRE_SMS FROM  MESSAGE WHERE  ID_MESSAGE=" + ID_MESSAGE);
                while (NBsms.next()) {
                    nbSMSF = NBsms.getInt("NMBRE_SMS");
                }

                if (Sold > ct * nbSMSF) {
                    newSolde = Sold - ct * nbSMSF;

                    pstmt6 = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE = " + newSolde + " WHERE  ID_UTILISATEUR = " + IDUSERSession);
                    saveResult4 = pstmt6.executeUpdate();

                    saveResult = pstmt.executeUpdate();

                    stmtObj = getConnection().createStatement();
                    idutDIF = stmtObj.executeQuery("SELECT  ID_LIST FROM  (SELECT ID_LIST FROM DIFFUSION_COMPAGNE ORDER BY ID_DIFFUSION DESC) where rownum = 1");
                    while (idutDIF.next()) {

                        ID_LISTE_GSM = idutDIF.getInt("ID_LIST");

                    }

                    stmtObj = getConnection().createStatement();
                    GSMDIF = stmtObj.executeQuery("SELECT  DETAIL_LIST_GSM.GSM FROM  LISTE_GSM,  DETAIL_LIST_GSM WHERE  LISTE_GSM.ID_UTILISATEUR=" + IDUSERSession + " AND  LISTE_GSM.ID_LISTE_GSM= DETAIL_LIST_GSM.ID_LIST AND  LISTE_GSM.ID_LISTE_GSM=" + ID_LISTE_GSM + " AND  DETAIL_LIST_GSM.GSM NOT IN ( SELECT  BLACK_LIST.GSM FROM  BLACK_LIST WHERE  BLACK_LIST.ID_UTILISATEUR=" + IDUSERSession + ")");

                    while (GSMDIF.next()) {

                        stmtObj = getConnection().createStatement();
                        idutDIF = stmtObj.executeQuery("SELECT  ID_DIFFUSION, ID_MESSAGE, ID_LIST FROM  (SELECT * FROM DIFFUSION_COMPAGNE ORDER BY ID_DIFFUSION DESC)where rownum = 1");
                        while (idutDIF.next()) {
                            IDDIFF = idutDIF.getInt("ID_DIFFUSION");
                            ID_MESSAGE = idutDIF.getInt("ID_MESSAGE");
                            ID_LISTE_GSM = idutDIF.getInt("ID_LIST");

                        }

                        stmtObj = getConnection().createStatement();
                        msgDIF = stmtObj.executeQuery("SELECT  MESSAGE.MESSAGE FROM  MESSAGE WHERE  MESSAGE.ID_MESSAGE=" + ID_MESSAGE);

                        while (msgDIF.next()) {
                            msgDIFF = msgDIF.getString("MESSAGE");
                        }

                        gsmDIFF = GSMDIF.getString("GSM");

                        int numGsm = 0;
                        numGsm = Integer.parseInt(gsmDIFF);

                        pstmt5 = getConnection().prepareStatement("INSERT INTO DETAILS_DIFFUSION(ID_DETAILS, ID_DIFFUSION, MESSAGE, GSM, ENTETE, ID_UTILISATEUR, DATE_ENVOIE) VALUES(ID_DETAILS_SEQ.NEXTVAL, " + IDDIFF + ", '" + msgDIFF + "', " + numGsm + ", '" + ENTET + "', '" + IDUSERSession + "', '" + s + "')");
                        saveResult1 = pstmt5.executeUpdate();
                    }

                }

                connObj.close();
            } else if (newAdminObj.getParametrable() == "true") {

                stmtObj = getConnection().createStatement();
                Statement stmtObj1 = getConnection().createStatement();
                Statement stmtObj2 = getConnection().createStatement();

                int SEUIL = 160;
                int SEUILAR = 70;
                int j = 1;

                Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");

                gtSolde = stmtObj.executeQuery("SELECT  SOLDE.SOLDE FROM  SOLDE WHERE  SOLDE.ID_UTILISATEUR=" + IDUSERSession);
                while (gtSolde.next()) {
                    Sold = gtSolde.getInt("SOLDE");
                }

                idutDIF = stmtObj.executeQuery("SELECT  ID_LIST,  DATE_ENVOIE FROM  (SELECT * FROM DIFFUSION_COMPAGNE ORDER BY ID_DIFFUSION DESC) where rownum = 1");

                while (idutDIF.next()) {

                    ID_LISTE_GSM = idutDIF.getInt("ID_LIST");
                    DE = idutDIF.getString("DATE_ENVOIE");
                }

                ctlIST = stmtObj1.executeQuery("SELECT GSM,KEY1,KEY2,KEY3,KEY4,KEY5,KEY6 FROM  DETAIL_LIST_GSM WHERE ID_LIST=" + ID_LISTE_GSM);
                while (ctlIST.next()) {

                    String gsm = ctlIST.getString("GSM");
                    k1 = ctlIST.getString("KEY1");
                    k2 = ctlIST.getString("KEY2");
                    k3 = ctlIST.getString("KEY3");
                    k4 = ctlIST.getString("KEY4");
                    k5 = ctlIST.getString("KEY5");
                    k6 = ctlIST.getString("KEY6");

                    NBsms = stmtObj.executeQuery("SELECT  MESSAGE FROM  MESSAGE WHERE  ID_MESSAGE=" + ID_MESSAGE);
                    while (NBsms.next()) {
                        value = NBsms.getString("MESSAGE");
                    }
                    value = value.replace("KEY1", k1);
                    value = value.replace("KEY2", k2);
                    value = value.replace("KEY3", k3);
                    value = value.replace("KEY4", k4);
                    value = value.replace("KEY5", k5);
                    value = value.replace("KEY6", k6);// replace space with hiphen
                    value = value.replace("'", "''");

                    Matcher matcher = pattern.matcher(value);
                    for (int i = 0; i < value.length(); i++) {
                        if (value.charAt(i) != ' ') {
                            countt++;
                        }
                    }

                    if (!matcher.matches()) {

                        while (countt > SEUILAR) {
                            SEUILAR = SEUILAR + 70;
                            j++;
                        }
                    }

                    if (matcher.matches()) {
                        while (countt > SEUIL) {
                            SEUIL = SEUIL + 160;
                            j++;
                        }
                    }
                    if (Sold > j) {
                        newSolde = Sold - j;

                        pstmt6 = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE = '" + newSolde + "' WHERE  ID_UTILISATEUR = " + IDUSERSession);
                        pstmt6.executeUpdate();
                        pstmt.executeUpdate();
                        idutDIF = stmtObj2.executeQuery("SELECT ID_DIFFUSION,ID_MESSAGE,ID_LIST FROM (SELECT * FROM DIFFUSION_COMPAGNE ORDER BY ID_DIFFUSION DESC) where rownum = 1");
                        while (idutDIF.next()) {
                            IDDIFF = idutDIF.getInt("ID_DIFFUSION");
                            ID_MESSAGE = idutDIF.getInt("ID_MESSAGE");
                            ID_LISTE_GSM = idutDIF.getInt("ID_LIST");
                        }
                        int numGsm = 0;
                        numGsm = Integer.parseInt(gsm);
                        System.out.println("8888888888888888888:::::::::::" + IDDIFF + "/" + value + "/" + numGsm + "/" + ENTET + "/" + IDUSERSession + "/" + DE);
                        pstmt5 = getConnection().prepareStatement("INSERT INTO DETAILS_DIFFUSION(ID_DETAILS, ID_DIFFUSION, MESSAGE, GSM, ENTETE, ID_UTILISATEUR, DATE_ENVOIE) VALUES(ID_DETAILS_SEQ.NEXTVAL, " + IDDIFF + ", '" + value + "', " + numGsm + ", '" + ENTET + "', " + IDUSERSession + ", '" + DE + "')");
                        pstmt5.executeUpdate();

                    }
                }

            }
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        if (saveResult != 0) {
            navigationResult = "UserProfil.xhtml?faces-redirect=true";
        } else {
            navigationResult = "UserProfil.xhtml?faces-redirect=true";
        }

        new DatabaseOperation().hist_action("The user with the name of " + SessionUtils.getUserName() + " ID=" + IDUSERSession + " Add Compagne planifié ");
        return navigationResult;

    }

    public static String saveUserDetailsInDB(AdminBean newAdminObj) throws SQLException {
        int saveResult = 0;
        int saveResult1 = 0;
        int saveResult2 = 0;
        int saveResult3 = 0;
        int saveResult4 = 0;
        int saveResult5 = 0;
        int saveResult6 = 0;
        int saveResult7 = 0;
        int IDUSER = 5555555;
        int IDADMIN = 5555555;
        String SoldCheck = "";
        String navigationResult = "";
        ResultSet idut;
        ResultSet idAD;
        ArrayList IDUTList = new ArrayList();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        int IDUSERSession = 0;
        ResultSet idutSession;
        String LOGIN = "";

        try {
            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            idutSession = stmtObj.executeQuery("SELECT LOGIN FROM UTILISATEUR");
            while (idutSession.next()) {

                LOGIN = idutSession.getString("LOGIN");
                System.out.println("GTWappOperation.DatabaseOperation.saveAdminDetailsInDB()" + LOGIN + "/////" + newAdminObj.getLogin() + "/////" + LOGIN.equals(newAdminObj.getLogin()));

                // System.out.println("GTWappOperation.DatabaseOperation.saveAdminDetailsInDB()"+ (LOGIN == newAdminObj.getLogin()));
                if (LOGIN.equals(newAdminObj.getLogin())) {
                    System.out.println("GTWappOperation.DatabaseOperation.saveAdminDetailsInDB() ooooooooooook");

                    FacesContext.getCurrentInstance().validationFailed();

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "login deja exist ", "login deja exist");
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    return null;
                }
            }

            pstmt = getConnection().prepareStatement("INSERT INTO SOLDE(ID_SOLDE , ID_UTILISATEUR , SOLDE ) VALUES(SOLDE_SEQ.NEXTVAL, ?, ?)");
            pstmt1 = getConnection().prepareStatement("INSERT INTO UTILISATEUR(ID_UTILISATEUR , NOM , CONTACT_NAME , CONTACT_GSM , LOGIN , PWD , DATE_CREATION , ACTIVATED) VALUES(UTILISATEUR_SEQ.NEXTVAL,?,?,?,?,?,?,?)");
            pstmt2 = getConnection().prepareStatement("INSERT INTO HISTORIQUE(ID_HISTORIQUE , ID_UTILISATEUR , SOLDE_INITIAL , SOLDE_FINAL , DATE_ALIMENTATION) VALUES(HISTORIQUE_SEQ.NEXTVAL, ?,?,?,?)");
            pstmt3 = getConnection().prepareStatement("INSERT INTO ROLEU(ID_ROLE , ID_UTILISATEUR , ROLE ) VALUES(ROLEU_SEQ.NEXTVAL, ?, 'User')");
            pstmt4 = getConnection().prepareStatement("INSERT INTO PARENT(ID_PARENT , ID_CHILD , ID_ADMIN ) VALUES(PARENT_SEQ.NEXTVAL,?," + IDUSERSession + ")");

            pstmt5 = getConnection().prepareStatement("INSERT INTO OPTIONU(ID_OPTION, ID_UTILISATEUR, AR , MODIF_ENTET , LIST_PARAM, DIFF_PLANIFIER, API) VALUES(OPTIONU_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)");
            pstmt6 = getConnection().prepareStatement("INSERT INTO ENTETE(ID_ENTETE, ID_UTILISATEUR, ENTETE) VALUES(ENTETE_SEQ.NEXTVAL,?, ?)");
            pstmt7 = getConnection().prepareStatement("INSERT INTO HISTORIQUE_ENTETE (ID_HISTORIQUE_ENTETE, ID_UTILISATEUR, ENTETE_INITIALE, ENTETE_FINALE, DATE_ENTETE) VALUES (HISTORIQUE_ENTETE_SEQ.NEXTVAL,?,?,?,?)");

            pstmt1.setString(1, newAdminObj.getNom());
            pstmt1.setString(2, newAdminObj.getContactName());
            pstmt1.setString(3, newAdminObj.getContactGsm());
            pstmt1.setString(4, newAdminObj.getLogin());
            pstmt1.setString(5, newAdminObj.getPwd());
            pstmt1.setString(6, dateFormat.format(date));
            pstmt1.setString(7, "True");

            resultSetObj = stmtObj.executeQuery("SELECT SOLDE.SOLDE FROM  UTILISATEUR,  SOLDE WHERE  UTILISATEUR.ID_UTILISATEUR= SOLDE.ID_UTILISATEUR AND  UTILISATEUR.ID_UTILISATEUR=" + IDUSERSession);
            while (resultSetObj.next()) {
                SoldCheck = resultSetObj.getString("SOLDE");
            }

            System.out.println("GTWappOperation.DatabaseOperation.saveUserDetailsInDB()ssssss" + newAdminObj.getSolde() + "/" + SoldCheck);
            if (Integer.parseInt(SoldCheck) >= Integer.parseInt(newAdminObj.getSolde())) {

                System.out.println("GTWappOperation.DatabaseOperation.saveUserDetailsInDB()" + SoldCheck);
                saveResult1 = pstmt1.executeUpdate();

                idut = stmtObj.executeQuery("SELECT ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.ID_UTILISATEUR = ( SELECT MAX( UTILISATEUR.ID_UTILISATEUR) FROM  UTILISATEUR )");
                while (idut.next()) {
                    IDUSER = idut.getInt("ID_UTILISATEUR");
                }

                pstmt.setInt(1, IDUSER);
                pstmt.setString(2, newAdminObj.getSolde());

                pstmt2.setInt(1, IDUSER);
                pstmt2.setString(2, newAdminObj.getSolde());
                pstmt2.setString(3, newAdminObj.getSolde());
                pstmt2.setString(4, dateFormat.format(date));

                pstmt3.setInt(1, IDUSER);

                pstmt4.setInt(1, IDUSER);

                pstmt5.setInt(1, IDUSER);

                pstmt5.setBoolean(2, newAdminObj.getAr());
                pstmt5.setBoolean(3, newAdminObj.getModifEntet());
                pstmt5.setBoolean(4, newAdminObj.getListParam());
                pstmt5.setBoolean(5, newAdminObj.getDiffPlanifier());
                pstmt5.setBoolean(6, newAdminObj.getApi());

                pstmt6.setInt(1, IDUSER);

                pstmt6.setString(2, newAdminObj.getEntete());

                pstmt7.setInt(1, IDUSER);
                pstmt7.setString(2, newAdminObj.getEntete());
                pstmt7.setString(3, newAdminObj.getEntete());
                pstmt7.setString(4, dateFormat.format(date));

                saveResult = pstmt.executeUpdate();
                saveResult2 = pstmt2.executeUpdate();
                saveResult3 = pstmt3.executeUpdate();
                saveResult4 = pstmt4.executeUpdate();
                saveResult5 = pstmt5.executeUpdate();
                saveResult6 = pstmt6.executeUpdate();
                saveResult7 = pstmt7.executeUpdate();
                String CalSolde = Integer.toString(Integer.parseInt(SoldCheck) - Integer.parseInt(newAdminObj.getSolde()));

                System.out.println("------------" + CalSolde + "-------------**" + IDUSER);
                idAD = stmtObj.executeQuery("SELECT ID_ADMIN FROM PARENT WHERE ID_CHILD=" + IDUSER);
                while (idAD.next()) {
                    IDADMIN = idAD.getInt("ID_ADMIN");
                }
                System.out.println("------------" + CalSolde + "-------------" + IDADMIN);
                pstmt8 = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE.SOLDE ='" + CalSolde + "' WHERE  SOLDE.ID_UTILISATEUR =" + IDADMIN);
                pstmt8.executeUpdate();
                new DatabaseOperation().hist_action("The user with the name of " + newAdminObj.getNom() + " Created by " + SessionUtils.getUserName());

            } else {
                System.out.println("GTWappOperation.DatabaseOperation.saveUserDetailsInDB()***********////888");
                FacesContext.getCurrentInstance().validationFailed();
                FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Verifier votre Solde ", "Verifier votre Solde");
                FacesContext.getCurrentInstance().addMessage(null, messages);
                return null;
            }
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        if (saveResult != 0) {
            navigationResult = "ListUser.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }

    public static void saveUserMessagesInDB(AdminBean newAdminObj) {
        int saveResult = 0;

        String navigationResult = "";
        ResultSet idut;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        int IDUSERSession = 0;
        String AR = "";
        ResultSet idutSession;
        ResultSet arresulst;
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            pstmt = getConnection().prepareStatement("INSERT INTO MESSAGE(ID_MESSAGE , ID_UTILISATEUR , NOM_MESSAGE, MESSAGE, DATE_CREATION_MESSAGE , LONGEUR, NMBRE_SMS) VALUES(MESSAGE_SEQ.NEXTVAL," + IDUSERSession + ", ?, ?, ?, ?, ?)");

            pstmt.setString(1, newAdminObj.getNomMessage());
            pstmt.setString(2, newAdminObj.getMessage());
            pstmt.setString(3, dateFormat.format(date));

            int count = 0;
            int SEUIL = 160;
            int SEUILAR = 70;
            int j = 1;

            final Pattern arabicLettersPattern = Pattern.compile("[\\u0600-\\u06FF\\u0750-\\u077F]",
                    Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE);

            final Matcher arabicLettersMatcher = arabicLettersPattern.matcher(newAdminObj.getMessage());

            //Counts each character except space    
            for (int i = 0; i < newAdminObj.getMessage().length(); i++) {
                if (newAdminObj.getMessage().charAt(i) != ' ') {
                    count++;
                }
            }
            pstmt.setInt(4, count);

            Matcher matcher = pattern.matcher(newAdminObj.getMessage());

            if (arabicLettersMatcher.find()) {
                arresulst = stmtObj.executeQuery("SELECT  OPTIONU.AR FROM  OPTIONU WHERE  OPTIONU.ID_UTILISATEUR=" + IDUSERSession);
                while (arresulst.next()) {
                    AR = arresulst.getString("AR");
                }

                if (AR.equals("1")) {

                    while (count > SEUILAR) {
                        SEUILAR = SEUILAR + 70;
                        j++;
                    }

                    pstmt.setInt(5, j);
                    saveResult = pstmt.executeUpdate();
                    connObj.close();
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('msgDialog').show();");

                } else if (AR.equals("0")) {

                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('errGSMDialog').show();");

                }

            } else {

                if (matcher.matches()) {
                    while (count > SEUIL) {
                        SEUIL = SEUIL + 160;
                        j++;
                    }
                }

                if (!matcher.matches()) {
                    while (count > SEUILAR) {
                        SEUILAR = SEUILAR + 70;
                        j++;
                    }
                }

                pstmt.setInt(5, j);
                saveResult = pstmt.executeUpdate();

                connObj.close();
                new DatabaseOperation().hist_action("The user with the name of " + SessionUtils.getUserName() + " ID=" + IDUSERSession + " Add message ");

                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('msgDialog').show();");

            }

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

    }

    public static void sendMessagesInDB(AdminBean newAdminObj) {
        int saveResult = 0;

        String navigationResult = "";
        ResultSet idut;
        ResultSet gtSolde;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        int IDUSERSession = 0;
        String entetR = "";
        String AR = "";
        ResultSet idutSession, entetResult;
        ResultSet arresulst;
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");

        int Sold = 0;
        int ct = 0;
        int countt = 0;
        int nbSMSF = 0;
        int newSolde = 0;
        int SEUILAR = 0;
        int SEUIL = 0;
        int j = 0;

        try {
            System.out.println("GTWappOperation.DatabaseOperation.sendMessagesInDB() oooooooook");

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  ID_UTILISATEUR FROM  UTILISATEUR WHERE  LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }
            System.out.println("GTWappOperation.DatabaseOperation.sendMessagesInDB()" + IDUSERSession);
            entetResult = stmtObj.executeQuery("SELECT  ENTETE FROM  ENTETE WHERE  ID_UTILISATEUR=" + IDUSERSession);
            while (entetResult.next()) {
                entetR = entetResult.getString("ENTETE");
            }
            System.out.println("GTWappOperation.DatabaseOperation.sendMessagesInDB()" + entetR);
            pstmt = getConnection().prepareStatement("INSERT INTO DETAILS_DIFFUSION(ID_DETAILS, ID_DIFFUSION, MESSAGE, GSM, ENTETE, ID_UTILISATEUR, DATE_ENVOIE) VALUES(ID_DETAILS_SEQ.NEXTVAL, 1,?,?, '" + entetR + "', " + IDUSERSession + ", ?)");

            pstmt.setString(1, newAdminObj.getMessage());

            pstmt.setInt(2, Integer.parseInt(newAdminObj.getGsm().replace("-", "")));

            System.out.println("GTWappOperation.DatabaseOperation.sendMessagesInDB()" + dateFormat.format(date));

            pstmt.setString(3, dateFormat.format(date));

            Matcher matcher = pattern.matcher(newAdminObj.getMessage());
            for (int i = 0; i < newAdminObj.getMessage().length(); i++) {
                if (newAdminObj.getMessage().charAt(i) != ' ') {
                    countt++;
                }
            }

            if (!matcher.matches()) {

                while (countt > SEUILAR) {
                    SEUILAR = SEUILAR + 70;
                    j++;
                }
            }

            if (matcher.matches()) {
                while (countt > SEUIL) {
                    SEUIL = SEUIL + 160;
                    j++;
                }
            }

            gtSolde = stmtObj.executeQuery("SELECT  SOLDE.SOLDE FROM  SOLDE WHERE  SOLDE.ID_UTILISATEUR=" + IDUSERSession);
            while (gtSolde.next()) {
                Sold = gtSolde.getInt("SOLDE");
            }
            if (Sold > j) {
                newSolde = Sold - j;
                pstmt6 = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE = '" + newSolde + "' WHERE  ID_UTILISATEUR = " + IDUSERSession);
                pstmt6.executeUpdate();
                saveResult = pstmt.executeUpdate();
            }

            connObj.close();
            new DatabaseOperation().hist_action("The user with the name of " + SessionUtils.getUserName() + " ID=" + IDUSERSession + " send rapid messages to " + Integer.parseInt(newAdminObj.getGsm().replace("-", "")));
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('SuccessGSMDialog').show();");

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

    }

    public static String saveEntetInDB(AdminBean newAdminObj) {
        int saveResult = 0;
        int saveResult1 = 0;
        int saveResult2 = 0;

        String navigationResult = "";

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        ResultSet entetResult;
        String entetR = null;

        ResultSet histentetResult;
        ResultSet histentetResultadd;
        String histentetR;
        String histentetRadd = null;

        int IDUSERSession = 0;
        ResultSet idutSession;

        try {
            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            entetResult = stmtObj.executeQuery("SELECT  ENTETE.ENTETE FROM  ENTETE WHERE  ENTETE.ID_UTILISATEUR=" + IDUSERSession);
            while (entetResult.next()) {
                entetR = entetResult.getString("ENTETE");
            }

            histentetResultadd = stmtObj.executeQuery("SELECT  HISTORIQUE_ENTETE.ENTETE_FINALE FROM  HISTORIQUE_ENTETE WHERE   HISTORIQUE_ENTETE.DATE_ENTETE IS NOT NULL AND  HISTORIQUE_ENTETE.ID_UTILISATEUR=" + IDUSERSession + " ORDER BY  HISTORIQUE_ENTETE.DATE_ENTETE ASC");
            while (histentetResultadd.next()) {
                histentetRadd = histentetResultadd.getString("ENTETE_FINALE");
            }

            histentetResult = stmtObj.executeQuery("SELECT * FROM  HISTORIQUE_ENTETE WHERE  HISTORIQUE_ENTETE.ID_UTILISATEUR=" + IDUSERSession);

            if (histentetResult.next() == false) {

                pstmt1 = getConnection().prepareStatement("INSERT INTO HISTORIQUE_ENTETE(ID_HISTORIQUE_ENTETE , ID_UTILISATEUR , ENTETE_INITIALE , ENTETE_FINALE , DATE_ENTETE) VALUES(HISTORIQUE_ENTETE_SEQ.NEXTVAL, " + IDUSERSession + ",'" + entetR + "', ?, ?)");

                pstmt1.setString(1, newAdminObj.getEnteteFinale());
                pstmt1.setString(2, dateFormat.format(date));
                pstmt2 = getConnection().prepareStatement("UPDATE  ENTETE SET ENTETE = '" + newAdminObj.getEnteteFinale() + "' WHERE  ENTETE.ID_UTILISATEUR =" + IDUSERSession);

                saveResult1 = pstmt1.executeUpdate();
                saveResult2 = pstmt2.executeUpdate();

                connObj.close();
            } else {
                pstmt1 = getConnection().prepareStatement("INSERT INTO HISTORIQUE_ENTETE(ID_HISTORIQUE_ENTETE , ID_UTILISATEUR , ENTETE_INITIALE , ENTETE_FINALE , DATE_ENTETE) VALUES(HISTORIQUE_ENTETE_SEQ.NEXTVAL," + IDUSERSession + ",'" + histentetRadd + "',?, ?)");

                pstmt1.setString(1, newAdminObj.getEnteteFinale());
                pstmt1.setString(2, dateFormat.format(date));

                pstmt2 = getConnection().prepareStatement("UPDATE  ENTETE SET ENTETE = '" + newAdminObj.getEnteteFinale() + "' WHERE  ENTETE.ID_UTILISATEUR =" + IDUSERSession);

                saveResult1 = pstmt1.executeUpdate();
                saveResult2 = pstmt2.executeUpdate();

                connObj.close();
            }
            new DatabaseOperation().hist_action("The user with the name of " + SessionUtils.getUserName() + " ID=" + IDUSERSession + " Add entet: " + newAdminObj.getEnteteFinale());

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        if (saveResult != 0) {
            navigationResult = "UserProfil.xhtml?faces-redirect=true";
        } else {
            navigationResult = "UserProfil.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }

    public static boolean saveEXLInDB(String Path, int idlist) throws FileNotFoundException, FileNotFoundException, IOException, SQLException {
        boolean numeric = true;
        int saveResult = 0;
        ResultSet idutSession, optionSession;
        int listparam = 0;
        int IDUSERSession = 0;

        int k = 1;

        String KEY1 = " ";
        String KEY2 = " ";
        String KEY3 = " ";
        String KEY4 = " ";
        String KEY5 = " ";
        String KEY6 = " ";
        String GSM = " ";
        ArrayList<Integer> WrongValue = new ArrayList<Integer>();

        System.out.println("save EXL" + Path + "and the id is " + idlist);
        stmtObj = getConnection().createStatement();

        idutSession = stmtObj.executeQuery("SELECT  ID_UTILISATEUR FROM  UTILISATEUR WHERE  LOGIN='" + SessionUtils.getUserName() + "'");
        while (idutSession.next()) {
            IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
        }

        optionSession = stmtObj.executeQuery("SELECT  LIST_PARAM FROM  OPTIONU WHERE  ID_UTILISATEUR=" + IDUSERSession);
        while (optionSession.next()) {
            listparam = optionSession.getInt("LIST_PARAM");
        }
        System.out.println("GTWappOperation.DatabaseOperation.saveEXLInDB()exp 10XSS" + listparam);

        if (listparam == 1) {
            System.out.println("GTWappOperation.DatabaseOperation.saveEXLInDB()exp 10");
            if (FilenameUtils.getExtension(Path).equals("csv")) {
                try (
                        Reader reader = Files.newBufferedReader(Paths.get(Path));
                        CSVReader csvReader = new CSVReader(reader);) {
                    // Reading Records One by One in a String array
                    System.out.println("GTWappOperation.DatabaseOperation.saveEXLInDB()exp 10");
                    String[] nextRecord;

                    while ((nextRecord = csvReader.readNext()) != null) {

                        GSM = (String) nextRecord[0];
                        System.out.println("GTWappOperation.DatabaseOperation.saveEXLInDB()exp aaaaaaaaaaaaaaaaaaaaaa");

                        if (nextRecord.length == 0) {
                        } else if (nextRecord.length == 1) {
                            GSM = (String) nextRecord[0];
                        } else if (nextRecord.length == 2) {
                            GSM = (String) nextRecord[0];
                            KEY1 = (String) nextRecord[1];
                        } else if (nextRecord.length == 3) {
                            GSM = (String) nextRecord[0];
                            KEY1 = (String) nextRecord[1];
                            KEY2 = (String) nextRecord[2];
                        } else if (nextRecord.length == 4) {
                            GSM = (String) nextRecord[0];
                            KEY1 = (String) nextRecord[1];
                            KEY2 = (String) nextRecord[2];
                            KEY3 = (String) nextRecord[3];
                        } else if (nextRecord.length == 5) {
                            GSM = (String) nextRecord[0];
                            KEY1 = (String) nextRecord[1];
                            KEY2 = (String) nextRecord[2];
                            KEY3 = (String) nextRecord[3];
                            KEY4 = (String) nextRecord[4];
                        } else if (nextRecord.length == 6) {
                            GSM = (String) nextRecord[0];
                            KEY1 = (String) nextRecord[1];
                            KEY2 = (String) nextRecord[2];
                            KEY3 = (String) nextRecord[3];
                            KEY4 = (String) nextRecord[4];
                            KEY5 = (String) nextRecord[5];

                        } else if (nextRecord.length == 7) {
                            GSM = (String) nextRecord[0];

                            KEY1 = (String) nextRecord[1];
                            KEY2 = (String) nextRecord[2];
                            KEY3 = (String) nextRecord[3];
                            KEY4 = (String) nextRecord[4];
                            KEY5 = (String) nextRecord[5];
                            KEY6 = (String) nextRecord[6];
                        }
                        int numGsm = 0;
                        numeric = true;
                        try {
                            numGsm = Integer.parseInt(GSM);
                        } catch (NumberFormatException e) {
                            k++;
                            WrongValue.add(k);

                            numeric = false;
                            continue;
                        }

                        System.out.println("Votre Gsm est " + numeric + "" + numGsm);
                        if (numeric && (GSM.length() == 8)) {
                            System.out.println("GTWappOperation.DatabaseOperation.saveEXLInDB()exp 10");
                            pstmt7 = getConnection().prepareStatement("INSERT INTO DETAIL_LIST_GSM(ID_DETAIL_LIST_GSM, ID_LIST, GSM, KEY1, KEY2, KEY3, KEY4, KEY5, KEY6) VALUES(DETAIL_LIST_GSM_SEQ.NEXTVAL," + idlist + ",'" + GSM + "','" + KEY1 + "','" + KEY2 + "','" + KEY3 + "','" + KEY4 + "','" + KEY5 + "', '" + KEY6 + "')");
                            saveResult = pstmt7.executeUpdate();

                        }

                    }
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Verifier votre gsm au niveau des lignes N:" + WrongValue, ""));
                    csvReader.close();
                    if (saveResult != 0) {

                        System.out.println("File Closed");
                        return true;
                    } else {
                        return false;
                    }

                }

            } else if (FilenameUtils.getExtension(Path).equals("xls")) {
                FileInputStream input = new FileInputStream(Path);
                POIFSFileSystem fs = new POIFSFileSystem(input);
                HSSFWorkbook wb = new HSSFWorkbook(fs);

                HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(0);
                Row row;

                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    GSM = null;

                    KEY1 = " ";
                    KEY2 = " ";
                    KEY3 = " ";
                    KEY4 = " ";
                    KEY5 = " ";
                    KEY6 = " ";

                    System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB" + row.getLastCellNum());
                    for (int gt = 1; gt < row.getLastCellNum(); gt++) {

                        if (gt == 1) {
                            if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                                int K1 = (int) row.getCell(1).getNumericCellValue();
                                KEY1 = Integer.toString(K1);
                            } else {
                                KEY1 = (String) row.getCell(1).toString();
                                System.out.println("KEY1" + KEY1);
                            }
                        }
                        if (gt == 2) {
                            if (row.getCell(2).getCellType() == CellType.NUMERIC) {
                                int K2 = (int) row.getCell(2).getNumericCellValue();
                                KEY2 = Integer.toString(K2);
                            } else {
                                KEY2 = (String) row.getCell(2).toString();
                                System.out.println("KEY2" + KEY2);
                            }
                        }
                        if (gt == 3) {
                            if (row.getCell(3).getCellType() == CellType.NUMERIC) {
                                int K3 = (int) row.getCell(3).getNumericCellValue();
                                KEY3 = Integer.toString(K3);
                            } else {
                                KEY3 = (String) row.getCell(3).toString();
                                System.out.println("KEY3" + KEY3);
                            }
                        }

                        if (gt == 4) {
                            if (row.getCell(4).getCellType() == CellType.NUMERIC) {
                                int K4 = (int) row.getCell(4).getNumericCellValue();
                                KEY4 = Integer.toString(K4);
                            } else {
                                KEY4 = (String) row.getCell(4).toString();
                                System.out.println("KEY4" + KEY4);
                            }
                        }
                        if (gt == 5) {
                            System.out.println("KEY5");
                            if (row.getCell(5).getCellType() == CellType.NUMERIC) {
                                int K5 = (int) row.getCell(5).getNumericCellValue();
                                KEY5 = Integer.toString(K5);
                            } else {
                                KEY5 = (String) row.getCell(5).toString();
                                System.out.println("KEY5" + KEY5);
                            }
                        }
                        if (gt == 6) {
                            if (row.getCell(6).getCellType() == CellType.NUMERIC) {
                                int K6 = (int) row.getCell(6).getNumericCellValue();
                                KEY6 = Integer.toString(K6);
                            } else {
                                KEY6 = (String) row.getCell(6).toString();
                                System.out.println("KEY6" + KEY6);

                            }
                        }
                    }

                    numeric = true;
                    try {
                        GSM = Integer.toString((int) Math.round(row.getCell(0).getNumericCellValue()));
                        int numGsm = Integer.parseInt(GSM);

                    } catch (IllegalStateException e) {
                        k++;
                        WrongValue.add(k);
                        numeric = false;
                    }
                    if (numeric && (GSM.length() == 8)) {

                        System.out.println("GTWappOperation.DatabaseOperation.saveEXLInDB()" + idlist + "," + GSM + "," + KEY1 + "," + KEY2 + "," + KEY3 + "," + KEY4 + "," + KEY5 + "," + KEY6);
                        pstmt7 = getConnection().prepareStatement("INSERT INTO DETAIL_LIST_GSM(ID_DETAIL_LIST_GSM, ID_LIST, GSM, KEY1, KEY2, KEY3, KEY4, KEY5, KEY6) VALUES(DETAIL_LIST_GSM_SEQ.NEXTVAL," + idlist + ",'" + GSM + "','" + KEY1 + "','" + KEY2 + "','" + KEY3 + "','" + KEY4 + "','" + KEY5 + "', '" + KEY6 + "')");

                        saveResult = pstmt7.executeUpdate();
                    }
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Verifier votre gsm au niveau des lignes N:" + WrongValue, ""));
                wb.close();
                if (saveResult != 0) {

                    System.out.println("File closed");
                    return true;
                } else {
                    return false;
                }
            } else if (FilenameUtils.getExtension(Path).equals("xlsx")) {

                InputStream ExcelFileToRead = new FileInputStream(Path);
                XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

                XSSFWorkbook test = new XSSFWorkbook();

                XSSFSheet sheet = wb.getSheetAt(0);
                XSSFRow row;
                XSSFCell cell;

                Iterator rows = sheet.rowIterator();

                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    GSM = null;

                    KEY1 = " ";
                    KEY2 = " ";
                    KEY3 = " ";
                    KEY4 = " ";
                    KEY5 = " ";
                    KEY6 = " ";

                    System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB" + row.getLastCellNum());
                    for (int gt = 1; gt < row.getLastCellNum(); gt++) {

                        if (gt == 1) {
                            if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                                int K1 = (int) row.getCell(1).getNumericCellValue();
                                KEY1 = Integer.toString(K1);
                            } else {
                                KEY1 = (String) row.getCell(1).toString();
                                System.out.println("KEY1" + KEY1);
                            }
                        }
                        if (gt == 2) {
                            if (row.getCell(2).getCellType() == CellType.NUMERIC) {
                                int K2 = (int) row.getCell(2).getNumericCellValue();
                                KEY2 = Integer.toString(K2);
                            } else {
                                KEY2 = (String) row.getCell(2).toString();
                                System.out.println("KEY2" + KEY2);
                            }
                        }
                        if (gt == 3) {
                            if (row.getCell(3).getCellType() == CellType.NUMERIC) {
                                int K3 = (int) row.getCell(3).getNumericCellValue();
                                KEY3 = Integer.toString(K3);
                            } else {
                                KEY3 = (String) row.getCell(3).toString();
                                System.out.println("KEY3" + KEY3);
                            }
                        }

                        if (gt == 4) {
                            if (row.getCell(4).getCellType() == CellType.NUMERIC) {
                                int K4 = (int) row.getCell(4).getNumericCellValue();
                                KEY4 = Integer.toString(K4);
                            } else {
                                KEY4 = (String) row.getCell(4).toString();
                                System.out.println("KEY4" + KEY4);
                            }
                        }
                        if (gt == 5) {
                            System.out.println("KEY5");
                            if (row.getCell(5).getCellType() == CellType.NUMERIC) {
                                int K5 = (int) row.getCell(5).getNumericCellValue();
                                KEY5 = Integer.toString(K5);
                            } else {
                                KEY5 = (String) row.getCell(5).toString();
                                System.out.println("KEY5" + KEY5);
                            }
                        }
                        if (gt == 6) {
                            if (row.getCell(6).getCellType() == CellType.NUMERIC) {
                                int K6 = (int) row.getCell(6).getNumericCellValue();
                                KEY6 = Integer.toString(K6);
                            } else {
                                KEY6 = (String) row.getCell(6).toString();
                                System.out.println("KEY6" + KEY6);

                            }
                        }
                    }

                    numeric = true;
                    try {
                        GSM = Integer.toString((int) Math.round(row.getCell(0).getNumericCellValue()));
                        int numGsm = Integer.parseInt(GSM);

                    } catch (IllegalStateException e) {
                        k++;
                        WrongValue.add(k);
                        numeric = false;
                    }
                    if (numeric && (GSM.length() == 8)) {

                        System.out.println("GTWappOperation.DatabaseOperation.saveEXLInDB()" + idlist + "," + GSM + "," + KEY1 + "," + KEY2 + "," + KEY3 + "," + KEY4 + "," + KEY5 + "," + KEY6);
                        pstmt7 = getConnection().prepareStatement("INSERT INTO DETAIL_LIST_GSM(ID_DETAIL_LIST_GSM, ID_LIST, GSM, KEY1, KEY2, KEY3, KEY4, KEY5, KEY6) VALUES(DETAIL_LIST_GSM_SEQ.NEXTVAL," + idlist + ",'" + GSM + "','" + KEY1 + "','" + KEY2 + "','" + KEY3 + "','" + KEY4 + "','" + KEY5 + "', '" + KEY6 + "')");

                        saveResult = pstmt7.executeUpdate();
                    }
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Verifier votre gsm au niveau des lignes N:" + WrongValue, ""));
                wb.close();
                if (saveResult != 0) {

                    System.out.println("File closed");
                    return true;
                } else {
                    return false;
                }
            }
        } else {

            if (FilenameUtils.getExtension(Path).equals("csv")) {
                try (
                        Reader reader = Files.newBufferedReader(Paths.get(Path));
                        CSVReader csvReader = new CSVReader(reader);) {
                    // Reading Records One by One in a String array
                    String[] nextRecord;
                    while ((nextRecord = csvReader.readNext()) != null) {

                         GSM = (String) nextRecord[0];

                        System.out.println("GTWappOperation.DatabaseOperation.saveEXLBKInDB()" + GSM + "///" + GSM.length());

                        int numGsm = 0;
                        numeric = true;
                        try {
                            numGsm = Integer.parseInt(GSM);
                        } catch (NumberFormatException e) {
                            k++;
                            WrongValue.add(k);

                            numeric = false;
                        }

                        System.out.println("Votre Gsm est " + numeric + "" + numGsm);
                        if (numeric && (GSM.length() == 8)) {

                            pstmt7 = getConnection().prepareStatement("INSERT INTO DETAIL_LIST_GSM(ID_DETAIL_LIST_GSM, ID_LIST, GSM) VALUES(DETAIL_LIST_GSM_SEQ.NEXTVAL," + idlist + ",'" + GSM + "')");
                            saveResult = pstmt7.executeUpdate();
                        }

                    }
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Verifier votre gsm au niveau des lignes N:" + WrongValue, ""));
                    csvReader.close();
                    if (saveResult != 0) {
                        System.out.println("File closed");

                        return true;
                    } else {
                        return false;
                    }

                }

            } else if (FilenameUtils.getExtension(Path).equals("xls")) {

                InputStream ExcelFileToRead = new FileInputStream(Path);
                XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

                XSSFWorkbook test = new XSSFWorkbook();

                XSSFSheet sheet = wb.getSheetAt(0);
                XSSFRow row;
                XSSFCell cell;

                Iterator rows = sheet.rowIterator();

                for (int gt = 0; gt <= sheet.getLastRowNum(); gt++) {
                    row = sheet.getRow(gt);

                    System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB" + row.getLastCellNum());
                    for (int i = 1; i < row.getLastCellNum(); i++) {
                        row = sheet.getRow(i);
                        System.out.println("oooooooook1222222");
                        row = sheet.getRow(i);
                         GSM = null;
                        numeric = true;
                        try {
                            GSM = Integer.toString((int) Math.round(row.getCell(0).getNumericCellValue()));
                            int numGsm = Integer.parseInt(GSM);
                            System.out.println("ok" + numGsm);

                        } catch (IllegalStateException e) {
                            k++;
                            WrongValue.add(k);
                            numeric = false;
                        }
                        if (numeric && (GSM.length() == 8)) {
                            pstmt7 = getConnection().prepareStatement("INSERT INTO DETAIL_LIST_GSM(ID_DETAIL_LIST_GSM, ID_LIST, GSM) VALUES(DETAIL_LIST_GSM_SEQ.NEXTVAL," + idlist + ",'" + GSM + "')");
                            saveResult = pstmt7.executeUpdate();
                        }
                    }
                }
                wb.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Verifier votre gsm au niveau des lignes N:" + WrongValue, ""));

                if (saveResult != 0) {
                    System.out.println("File closed");
                    return true;
                } else {
                    return false;
                }
            } else if (FilenameUtils.getExtension(Path).equals("xlsx")) {

                InputStream ExcelFileToRead = new FileInputStream(Path);
                XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

                XSSFWorkbook test = new XSSFWorkbook();

                XSSFSheet sheet = wb.getSheetAt(0);
                XSSFRow row;
                XSSFCell cell;

                Iterator rows = sheet.rowIterator();

                while (rows.hasNext()) {
                    row = (XSSFRow) rows.next();
                    Iterator cells = row.cellIterator();
                     GSM = null;
                    numeric = true;
                    try {
                        GSM = Integer.toString((int) Math.round(row.getCell(0).getNumericCellValue()));
                        int numGsm = Integer.parseInt(GSM);

                    } catch (IllegalStateException e) {
                        k++;
                        WrongValue.add(k);
                        numeric = false;
                    }
                    if (numeric && (GSM.length() == 8)) {
                        pstmt7 = getConnection().prepareStatement("INSERT INTO DETAIL_LIST_GSM(ID_DETAIL_LIST_GSM, ID_LIST, GSM) VALUES(DETAIL_LIST_GSM_SEQ.NEXTVAL," + idlist + ",'" + GSM + "')");
                        saveResult = pstmt7.executeUpdate();
                    }
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Verifier votre gsm au niveau des lignes N:" + WrongValue, ""));
                wb.close();
                if (saveResult != 0) {

                    System.out.println("File closed");
                    return true;
                } else {
                    return false;
                }

            }

        }

        return false;
    }

    public static boolean saveEXLBKInDB(String Path) throws FileNotFoundException, FileNotFoundException, IOException, SQLException {
        int saveResult = 0;
        ResultSet idutSession;
        int IDUSERSession = 0;
        boolean numeric = true;
        int k = 1;
        ArrayList<Integer> WrongValue = new ArrayList<Integer>();
        stmtObj = getConnection().createStatement();
        idutSession = stmtObj.executeQuery("SELECT  ID_UTILISATEUR FROM  UTILISATEUR WHERE  LOGIN='" + SessionUtils.getUserName() + "'");
        while (idutSession.next()) {
            IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
        }

        if (FilenameUtils.getExtension(Path).equals("csv")) {
            try (
                    Reader reader = Files.newBufferedReader(Paths.get(Path));
                    CSVReader csvReader = new CSVReader(reader);) {
                // Reading Records One by One in a String array
                String[] nextRecord;
                while ((nextRecord = csvReader.readNext()) != null) {

                    String GSM = (String) nextRecord[0];

                    System.out.println("GTWappOperation.DatabaseOperation.saveEXLBKInDB()" + GSM + "///" + GSM.length());

                    int numGsm = 0;
                    numeric = true;
                    try {
                        numGsm = Integer.parseInt(GSM);
                    } catch (NumberFormatException e) {
                        k++;
                        WrongValue.add(k);

                        numeric = false;
                    }

                    System.out.println("Votre Gsm est " + numeric + "" + numGsm);
                    if (numeric && (GSM.length() == 8)) {

                        pstmt7 = getConnection().prepareStatement("INSERT INTO black_list(id_black_list, id_utilisateur, gsm) VALUES (BLACK_LIST_SEQ.NEXTVAL,'" + IDUSERSession + "', '" + GSM + "')");
                        saveResult = pstmt7.executeUpdate();
                    }

                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Verifier votre gsm au niveau des lignes N:" + WrongValue, ""));
                csvReader.close();
                if (saveResult != 0) {
                    System.out.println("File closed");

                    return true;
                } else {
                    return false;
                }

            }

        } else if (FilenameUtils.getExtension(Path).equals("xls")) {

            InputStream ExcelFileToRead = new FileInputStream(Path);
            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

            XSSFWorkbook test = new XSSFWorkbook();

            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;

            Iterator rows = sheet.rowIterator();

            for (int gt = 0; gt <= sheet.getLastRowNum(); gt++) {
                row = sheet.getRow(gt);

                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB" + row.getLastCellNum());
                for (int i = 1; i < row.getLastCellNum(); i++) {
                    row = sheet.getRow(i);
                    System.out.println("oooooooook1222222");
                    row = sheet.getRow(i);
                    String GSM = null;
                    numeric = true;
                    try {
                        GSM = Integer.toString((int) Math.round(row.getCell(0).getNumericCellValue()));
                        int numGsm = Integer.parseInt(GSM);
                        System.out.println("ok" + numGsm);

                    } catch (IllegalStateException e) {
                        k++;
                        WrongValue.add(k);
                        numeric = false;
                    }
                    if (numeric && (GSM.length() == 8)) {
                        pstmt7 = getConnection().prepareStatement("INSERT INTO black_list(id_black_list, id_utilisateur, gsm) VALUES (BLACK_LIST_SEQ.NEXTVAL,'" + IDUSERSession + "', '" + GSM + "')");
                        saveResult = pstmt7.executeUpdate();
                    }
                }
            }
            wb.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Verifier votre gsm au niveau des lignes N:" + WrongValue, ""));

            if (saveResult != 0) {
                System.out.println("File closed");
                return true;
            } else {
                return false;
            }
        } else if (FilenameUtils.getExtension(Path).equals("xlsx")) {

            InputStream ExcelFileToRead = new FileInputStream(Path);
            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

            XSSFWorkbook test = new XSSFWorkbook();

            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;

            Iterator rows = sheet.rowIterator();

            while (rows.hasNext()) {
                row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                String GSM = null;
                numeric = true;
                try {
                    GSM = Integer.toString((int) Math.round(row.getCell(0).getNumericCellValue()));
                    int numGsm = Integer.parseInt(GSM);

                } catch (IllegalStateException e) {
                    k++;
                    WrongValue.add(k);
                    numeric = false;
                }
                if (numeric && (GSM.length() == 8)) {
                    pstmt7 = getConnection().prepareStatement("INSERT INTO black_list(id_black_list, id_utilisateur, gsm) VALUES (BLACK_LIST_SEQ.NEXTVAL,'" + IDUSERSession + "', '" + GSM + "')");
                    saveResult = pstmt7.executeUpdate();
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Verifier votre gsm au niveau des lignes N:" + WrongValue, ""));
            wb.close();
            if (saveResult != 0) {

                System.out.println("File closed");
                return true;
            } else {
                return false;
            }

        }
        return numeric;
    }

    public static void saveGSMInDB(AdminBean newAdminObj, String Path) {

        int saveResult = 0;
        String navigationResult = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        int IDUSERSession = 0;
        int IDLISTGSM = 0;
        ResultSet idutSession;
        ResultSet iduLGSMSession;
        Boolean validGsm = false;

        try {

            stmtObj = getConnection().createStatement();
            idutSession = stmtObj.executeQuery("SELECT  ID_UTILISATEUR FROM  UTILISATEUR WHERE  LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            pstmt1 = getConnection().prepareStatement("INSERT INTO LISTE_GSM( ID_LISTE_GSM , ID_UTILISATEUR , NOM_LISTE , DATE_CREATION_LISTE , PARAMETRABLE ) VALUES(LISTE_GSM_SEQ.NEXTVAL," + IDUSERSession + ", ?, ?, 'True')");

            pstmt1.setString(1, newAdminObj.getNomListe());
            pstmt1.setString(2, dateFormat.format(date));

            pstmt1.executeUpdate();

            stmtObj = getConnection().createStatement();
            iduLGSMSession = stmtObj.executeQuery("SELECT   ID_LISTE_GSM FROM (SELECT ID_LISTE_GSM FROM LISTE_GSM ORDER BY ID_LISTE_GSM DESC) where rownum = 1 ");
            while (iduLGSMSession.next()) {
                IDLISTGSM = iduLGSMSession.getInt("ID_LISTE_GSM");
            }
            pstmt2 = getConnection().prepareStatement("DELETE FROM  LISTE_GSM WHERE ID_LISTE_GSM =" + IDLISTGSM);
            System.out.println("GTWappOperation.DatabaseOperation.saveGSMInDB()" + Path);

            validGsm = saveEXLInDB(Path, IDLISTGSM);
            System.out.println("valid gsm " + validGsm);
            if (validGsm == true) {

                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('listDialog').show();");

            } else {
                pstmt2.executeUpdate();
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('confirmlistDialogwidgetno').show();");

            }

            connObj.close();
            new DatabaseOperation().hist_action("The user with the name of " + SessionUtils.getUserName() + " ID=" + IDUSERSession + " Add List GSM ");
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

    }

    public static void editAdminRecordInDB(int Id) {
        AdminBean editRecord = null;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  UTILISATEUR,  SOLDE WHERE  UTILISATEUR.ID_UTILISATEUR= SOLDE.ID_UTILISATEUR AND  UTILISATEUR.ID_UTILISATEUR=" + Id);
            if (resultSetObj != null) {
                resultSetObj.next();
                editRecord = new AdminBean();
                editRecord.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecord.setNom(resultSetObj.getString("NOM"));
                editRecord.setContactName(resultSetObj.getString("CONTACT_NAME"));
                editRecord.setContactGsm(resultSetObj.getString("CONTACT_GSM"));
                editRecord.setLogin(resultSetObj.getString("LOGIN"));
                editRecord.setPwd(resultSetObj.getString("PWD"));
                editRecord.setDateCreation(resultSetObj.getString("DATE_CREATION"));
                editRecord.setActivated(resultSetObj.getString("ACTIVATED"));
                editRecord.setIdSolde(resultSetObj.getInt("ID_SOLDE"));
                editRecord.setSolde(resultSetObj.getString("SOLDE"));

            }
            sessionMapObj.put("adminBeanEdit", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('modifAdminDialog').show();");

    }

    public static void editBlackListRecordInDB(int Id) {
        AdminBean editRecord = null;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  BLACK_LIST WHERE  BLACK_LIST.ID_BLACK_LIST=" + Id);
            if (resultSetObj != null) {
                resultSetObj.next();
                editRecord = new AdminBean();
                editRecord.setIdblacklist(resultSetObj.getInt("ID_BLACK_LIST"));
                editRecord.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecord.setGsmblacklist(resultSetObj.getString("GSM"));

            }
            sessionMapObj.put("BlackListEdit", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('editBlackListDialog').show();");

    }

    public static void savesaveOneGsmListRecordInDB(int Id) {
        AdminBean editRecord = null;

        ResultSet idutSession, optionSession;
        int listparam = 0;
        int IDUSERSession = 0;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            optionSession = stmtObj.executeQuery("SELECT  OPTIONU.LIST_PARAM FROM  OPTIONU WHERE  OPTIONU.ID_UTILISATEUR=" + IDUSERSession);
            while (optionSession.next()) {
                listparam = optionSession.getInt("LIST_PARAM");
            }

            if (listparam == 1) {

                stmtObj = getConnection().createStatement();
                resultSetObj = stmtObj.executeQuery("SELECT * FROM  DETAIL_LIST_GSM WHERE ID_LIST=" + Id + "ORDER BY  DETAIL_LIST_GSM.ID_DETAIL_LIST_GSM DESC");
                if (resultSetObj != null) {
                    resultSetObj.next();
                    editRecord = new AdminBean();

                    editRecord.setIdList(resultSetObj.getInt("ID_LIST"));
                    editRecord.setGsm(resultSetObj.getString("GSM"));
                    editRecord.setKey1(resultSetObj.getString("KEY1"));
                    editRecord.setKey2(resultSetObj.getString("KEY2"));
                    editRecord.setKey3(resultSetObj.getString("KEY3"));
                    editRecord.setKey4(resultSetObj.getString("KEY4"));
                    editRecord.setKey5(resultSetObj.getString("KEY5"));
                    editRecord.setKey6(resultSetObj.getString("KEY6"));

                }
                sessionMapObj.put("ONEGSMListSave", editRecord);
                connObj.close();

            } else {

                stmtObj = getConnection().createStatement();
                resultSetObj = stmtObj.executeQuery("SELECT ID_LIST,GSM FROM  DETAIL_LIST_GSM WHERE ID_LIST=" + Id + "ORDER BY  DETAIL_LIST_GSM.ID_DETAIL_LIST_GSM DESC");
                if (resultSetObj != null) {
                    resultSetObj.next();
                    editRecord = new AdminBean();

                    editRecord.setIdList(resultSetObj.getInt("ID_LIST"));
                    editRecord.setGsm(resultSetObj.getString("GSM"));

                }
                sessionMapObj.put("ONEGSMListSave", editRecord);
                connObj.close();

            }

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('createGSMListDetail').show();");

    }

    public static void editDetailListGsmRecordInDB(int Id) {
        AdminBean editRecord = null;

        ResultSet idutSession, optionSession;
        int listparam = 0;
        int IDUSERSession = 0;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            optionSession = stmtObj.executeQuery("SELECT  OPTIONU.LIST_PARAM FROM  OPTIONU WHERE  OPTIONU.ID_UTILISATEUR=" + IDUSERSession);
            while (optionSession.next()) {
                listparam = optionSession.getInt("LIST_PARAM");
            }

            if (listparam == 1) {
                resultSetObj = stmtObj.executeQuery("SELECT * FROM  DETAIL_LIST_GSM WHERE  DETAIL_LIST_GSM.ID_DETAIL_LIST_GSM=" + Id);
                if (resultSetObj != null) {
                    resultSetObj.next();

                    editRecord = new AdminBean();
                    editRecord.setIdDetailListGsm(resultSetObj.getInt("ID_DETAIL_LIST_GSM"));
                    editRecord.setIdList(resultSetObj.getInt("ID_LIST"));
                    editRecord.setGsm(resultSetObj.getString("GSM"));
                    editRecord.setKey1(resultSetObj.getString("KEY1"));
                    editRecord.setKey2(resultSetObj.getString("KEY2"));
                    editRecord.setKey3(resultSetObj.getString("KEY3"));
                    editRecord.setKey4(resultSetObj.getString("KEY4"));
                    editRecord.setKey5(resultSetObj.getString("KEY5"));
                    editRecord.setKey6(resultSetObj.getString("KEY6"));

                }
                sessionMapObj.put("DetailsListGsmEdit", editRecord);
                connObj.close();

            } else if (listparam == 0) {
                resultSetObj = stmtObj.executeQuery("SELECT ID_DETAIL_LIST_GSM,ID_LIST,GSM FROM DETAIL_LIST_GSM WHERE  DETAIL_LIST_GSM.ID_DETAIL_LIST_GSM=" + Id);
                if (resultSetObj != null) {
                    resultSetObj.next();

                    editRecord = new AdminBean();
                    editRecord.setIdDetailListGsm(resultSetObj.getInt("ID_DETAIL_LIST_GSM"));
                    editRecord.setIdList(resultSetObj.getInt("ID_LIST"));
                    editRecord.setGsm(resultSetObj.getString("GSM"));

                }
                sessionMapObj.put("DetailsListGsmEdit", editRecord);
                connObj.close();
            }
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('editDetailListGsmUpd').show();");

    }

    public static void DisplaybuttonHistCompRecordInDB(int Id) {
        AdminBean editRecord = null;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            System.out.println("GTWappOperation.DatabaseOperation.DisplaybuttonHistCompRecordInDB()" + Id);
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  HISTORIQUE_DIFFUSION WHERE  HISTORIQUE_DIFFUSION.ID_DIFFUSION=" + Id + "  and rownum = 1");
            if (resultSetObj != null) {
                resultSetObj.next();

                editRecord = new AdminBean();
                editRecord.setIdHistDiffusion(resultSetObj.getInt("ID_HISTORIQUE_DIFFUSION"));
                editRecord.setIdDiffusion(resultSetObj.getInt("ID_DIFFUSION"));
                editRecord.setSmsid(resultSetObj.getString("SMSID"));
                editRecord.setStatus(resultSetObj.getString("STATUS"));
                editRecord.setGsm(resultSetObj.getString("GSM"));
                editRecord.setMessage(resultSetObj.getString("MESSAGE"));
                editRecord.setEntete(resultSetObj.getString("ENTETE"));
                editRecord.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecord.setDateEnvoie(resultSetObj.getString("DATE_ENVOIE"));

            }
            sessionMapObj.put("DisplayListHistComp", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('detailslistHistcompDialog').show();");

    }

    public static void displaybuttonPieHistCompRecordInDB(int Id) {
        AdminBean editRecord = null;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  HISTORIQUE_DIFFUSION WHERE  HISTORIQUE_DIFFUSION.ID_DIFFUSION=" + Id + " FETCH FIRST ROW ONLY");
            if (resultSetObj != null) {
                resultSetObj.next();

                editRecord = new AdminBean();
                editRecord.setIdHistDiffusion(resultSetObj.getInt("ID_HISTORIQUE_DIFFUSION"));
                editRecord.setIdDiffusion(resultSetObj.getInt("ID_DIFFUSION"));
                editRecord.setSmsid(resultSetObj.getString("SMSID"));
                editRecord.setStatus(resultSetObj.getString("STATUS"));
                editRecord.setGsm(resultSetObj.getString("GSM"));
                editRecord.setMessage(resultSetObj.getString("MESSAGE"));
                editRecord.setEntete(resultSetObj.getString("ENTETE"));
                editRecord.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecord.setDateEnvoie(resultSetObj.getString("DATE_ENVOIE"));

            }
            sessionMapObj.put("DisplayListHistPieComp", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('ListTableHistPieDialog').show();");

    }

    public static void DisplaybuttonHistCompAPIRecordInDB(AdminBean newAdminObj) {

        ResultSet idut;
        int IDUSER = 0;
        String sta;
        ArrayList MsgList = new ArrayList();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        AdminBean editRecord = null;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {

            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            String datedebut = dateFormat.format(newAdminObj.getDatetimedebut());
            String datedefin = dateFormat.format(newAdminObj.getDatetimfin());

            resultSetObj = stmtObj.executeQuery("SELECT * FROM  HISTORIQUE_DIFFUSION WHERE ID_DIFFUSION=0 AND ID_UTILISATEUR=" + IDUSER + " AND DATE_ENVOIE BETWEEN '" + datedebut + "' AND '" + datedefin + "' and rownum = 1");
            if (resultSetObj != null) {
                resultSetObj.next();

                editRecord = new AdminBean();
                editRecord.setIdHistDiffusion(resultSetObj.getInt("ID_HISTORIQUE_DIFFUSION"));
                editRecord.setIdDiffusion(resultSetObj.getInt("ID_DIFFUSION"));
                editRecord.setSmsid(resultSetObj.getString("SMSID"));
                editRecord.setStatus(resultSetObj.getString("STATUS"));
                editRecord.setGsm(resultSetObj.getString("GSM"));
                editRecord.setMessage(resultSetObj.getString("MESSAGE"));
                editRecord.setEntete(resultSetObj.getString("ENTETE"));
                editRecord.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecord.setDateEnvoie(resultSetObj.getString("DATE_ENVOIE"));

            }
            sessionMapObj.put("DisplayListHistCompAPI", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('detailslistHistcompAPIDialog').show();");

    }

    public static void DisplaybuttonRecordInDB(int Id) {
        AdminBean editRecord = null;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            System.out.println("GTWappOperation.DatabaseOperation.DisplaybuttonRecordInDB()" + Id);
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  DETAIL_LIST_GSM WHERE  ID_LIST=" + Id + " and rownum = 1 ");
            if (resultSetObj != null) {
                resultSetObj.next();

                editRecord = new AdminBean();
                editRecord.setIdDetailListGsm(resultSetObj.getInt("ID_DETAIL_LIST_GSM"));
                editRecord.setIdList(resultSetObj.getInt("ID_LIST"));
                editRecord.setGsm(resultSetObj.getString("GSM"));
                editRecord.setKey1(resultSetObj.getString("KEY1"));
                editRecord.setKey2(resultSetObj.getString("KEY2"));
                editRecord.setKey3(resultSetObj.getString("KEY3"));
                editRecord.setKey4(resultSetObj.getString("KEY4"));
                editRecord.setKey5(resultSetObj.getString("KEY5"));
                editRecord.setKey6(resultSetObj.getString("KEY6"));

            }
            sessionMapObj.put("DisplayListGsm", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('detailslistDialog').show();");

    }

    public static void DeleteBlackListInDB(int Id) {
        AdminBean editRecord = null;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  BLACK_LIST WHERE  BLACK_LIST.ID_BLACK_LIST=" + Id);
            if (resultSetObj != null) {
                resultSetObj.next();
                editRecord = new AdminBean();
                editRecord.setIdblacklist(resultSetObj.getInt("ID_BLACK_LIST"));
                editRecord.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecord.setGsmblacklist(resultSetObj.getString("GSM"));

            }
            sessionMapObj.put("BlackListEdit", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('deleteBlackListDialog').show();");

    }

    public static void DeleteDetListGsmInDB(int Id) {

        AdminBean editRecord = null;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  DETAIL_LIST_GSM WHERE  DETAIL_LIST_GSM.ID_DETAIL_LIST_GSM=" + Id);
            if (resultSetObj != null) {
                resultSetObj.next();

                editRecord = new AdminBean();
                editRecord.setIdDetailListGsm(resultSetObj.getInt("ID_DETAIL_LIST_GSM"));
                editRecord.setIdList(resultSetObj.getInt("ID_LIST"));
                editRecord.setGsm(resultSetObj.getString("GSM"));
                editRecord.setKey1(resultSetObj.getString("KEY1"));
                editRecord.setKey2(resultSetObj.getString("KEY2"));
                editRecord.setKey3(resultSetObj.getString("KEY3"));
                editRecord.setKey4(resultSetObj.getString("KEY4"));
                editRecord.setKey5(resultSetObj.getString("KEY5"));
                editRecord.setKey6(resultSetObj.getString("KEY6"));

            }
            sessionMapObj.put("DetailsListGsmEdit", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('deletedeleteListGsmDialog').show();");

    }

    public static void deleteBlackListRecordInDB(AdminBean updateAdminObj) {

        try {

            pstmt = getConnection().prepareStatement("DELETE FROM  BLACK_LIST WHERE ID_BLACK_LIST =?");

            pstmt.setInt(1, updateAdminObj.getIdblacklist());

            pstmt.executeUpdate();

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        new DatabaseOperation().hist_action("The user Delete from black list the number " + updateAdminObj.getGsmblacklist());

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('BlackListDialog').show();");
    }

    public static void deleteGSMListDetailsRecordInDB(AdminBean updateAdminObj) {
        try {
            pstmt = getConnection().prepareStatement("DELETE FROM  DETAIL_LIST_GSM WHERE ID_DETAIL_LIST_GSM =?");

            pstmt.setInt(1, updateAdminObj.getIdDetailListGsm());

            pstmt.executeUpdate();

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('detailslistDialog').show();");
    }

    public static ArrayList showAdminByID(int idUtilisateur) {

        ArrayList AdminView = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT *FROM  UTILISATEUR,  SOLDE WHERE   UTILISATEUR.ID_UTILISATEUR=" + idUtilisateur + "  AND  UTILISATEUR.ID_UTILISATEUR= SOLDE.ID_UTILISATEUR");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();

                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setNom(resultSetObj.getString("NOM"));
                AdminObj.setContactName(resultSetObj.getString("CONTACT_NAME"));
                AdminObj.setContactGsm(resultSetObj.getString("CONTACT_GSM"));
                AdminObj.setLogin(resultSetObj.getString("LOGIN"));
                AdminObj.setPwd(resultSetObj.getString("PWD"));
                AdminObj.setDateCreation(resultSetObj.getString("DATE_CREATION"));
                AdminObj.setActivated(resultSetObj.getString("ACTIVATED"));
                AdminObj.setIdSolde(resultSetObj.getInt("ID_SOLDE"));
                AdminObj.setSolde(resultSetObj.getString("SOLDE"));

                AdminView.add(AdminObj);

            }
            //sessionMapObj.put("Hist", HistList);

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return AdminView;
    }

    public static ArrayList showUserByID(int idUtilisateur) {

        ArrayList AdminView = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT *FROM  UTILISATEUR,  SOLDE WHERE   UTILISATEUR.ID_UTILISATEUR=" + idUtilisateur + "  AND  UTILISATEUR.ID_UTILISATEUR= SOLDE.ID_UTILISATEUR");
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();

                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setNom(resultSetObj.getString("NOM"));
                AdminObj.setContactName(resultSetObj.getString("CONTACT_NAME"));
                AdminObj.setContactGsm(resultSetObj.getString("CONTACT_GSM"));
                AdminObj.setLogin(resultSetObj.getString("LOGIN"));
                AdminObj.setPwd(resultSetObj.getString("PWD"));
                AdminObj.setDateCreation(resultSetObj.getString("DATE_CREATION"));
                AdminObj.setActivated(resultSetObj.getString("ACTIVATED"));
                AdminObj.setIdSolde(resultSetObj.getInt("ID_SOLDE"));
                AdminObj.setSolde(resultSetObj.getString("SOLDE"));

                AdminView.add(AdminObj);

            }
            //sessionMapObj.put("Hist", HistList);

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return AdminView;
    }

    public static void editUserRecordInDB(int Id) {
        AdminBean editRecord = null;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {

            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  UTILISATEUR,  SOLDE,  OPTIONU, ENTETE WHERE  UTILISATEUR.ID_UTILISATEUR= SOLDE.ID_UTILISATEUR AND  SOLDE.ID_UTILISATEUR=  OPTIONU.ID_UTILISATEUR AND  OPTIONU.ID_UTILISATEUR=  ENTETE.ID_UTILISATEUR AND  UTILISATEUR.ID_UTILISATEUR=" + Id);

            if (resultSetObj != null) {
                resultSetObj.next();
                editRecord = new AdminBean();
                editRecord.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecord.setNom(resultSetObj.getString("NOM"));
                editRecord.setContactName(resultSetObj.getString("CONTACT_NAME"));
                editRecord.setContactGsm(resultSetObj.getString("CONTACT_GSM"));
                editRecord.setLogin(resultSetObj.getString("LOGIN"));
                editRecord.setPwd(resultSetObj.getString("PWD"));
                editRecord.setDateCreation(resultSetObj.getString("DATE_CREATION"));
                editRecord.setActivated(resultSetObj.getString("ACTIVATED"));

                editRecord.setIdSolde(resultSetObj.getInt("ID_SOLDE"));
                editRecord.setSolde(resultSetObj.getString("SOLDE"));

                editRecord.setIdOption(resultSetObj.getInt("ID_OPTION"));
                editRecord.setAr(resultSetObj.getBoolean("AR"));
                editRecord.setModifEntet(resultSetObj.getBoolean("MODIF_ENTET"));
                editRecord.setListParam(resultSetObj.getBoolean("LIST_PARAM"));
                editRecord.setDiffPlanifier(resultSetObj.getBoolean("DIFF_PLANIFIER"));
                editRecord.setApi(resultSetObj.getBoolean("API"));

                editRecord.setIdEntete(resultSetObj.getInt("ID_ENTETE"));
                editRecord.setEntete(resultSetObj.getString("ENTETE"));

            }
            sessionMapObj.put("userBeanEdit", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('editUserDialog').show();");

    }

    public static void SimulateMobInDB(String nomMessage) {

        System.out.println("GTWappOperation.DatabaseOperation.SimulateMobInDB()" + nomMessage);

        AdminBean editRecord = null;
        ResultSet idutSession;
        int IDUSERSession = 0;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            stmtObj = getConnection().createStatement();

            resultSetObj = stmtObj.executeQuery("SELECT * FROM  MESSAGE,  ENTETE WHERE  MESSAGE.ID_UTILISATEUR=" + IDUSERSession + " AND  ENTETE.ID_UTILISATEUR= MESSAGE.ID_UTILISATEUR AND  MESSAGE.NOM_MESSAGE='" + nomMessage + "'");

            if (resultSetObj != null) {
                resultSetObj.next();
                editRecord = new AdminBean();
                editRecord.setIdMessage(resultSetObj.getInt("ID_MESSAGE"));
                editRecord.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecord.setNomMessage(resultSetObj.getString("NOM_MESSAGE"));
                editRecord.setMessage(resultSetObj.getString("MESSAGE"));
                editRecord.setDateCreationMessage(resultSetObj.getString("DATE_CREATION_MESSAGE"));
                editRecord.setLongeur(resultSetObj.getInt("LONGEUR"));
                editRecord.setNmbreSms(resultSetObj.getInt("NMBRE_SMS"));
                editRecord.setIdEntete(resultSetObj.getInt("ID_ENTETE"));

                editRecord.setEntete(resultSetObj.getString("ENTETE"));

            }
            sessionMapObj.put("simBean", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        System.out.println("nom message est" + nomMessage);
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('simuGSMDialog').show();");

    }

    public static void SimReaMobInDB(String message) {

        System.out.println("GTWappOperation.DatabaseOperation.SimulateMobInDB()" + message);
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");

        AdminBean editRecord = null;
        ResultSet idutSession;
        int IDUSERSession = 0;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
            }

            stmtObj = getConnection().createStatement();

            resultSetObj = stmtObj.executeQuery("SELECT * FROM  ENTETE WHERE  ENTETE.ID_UTILISATEUR=" + IDUSERSession);

            if (resultSetObj != null) {
                resultSetObj.next();
                editRecord = new AdminBean();
                editRecord.setIdEntete(resultSetObj.getInt("ID_ENTETE"));
                editRecord.setEntete(resultSetObj.getString("ENTETE"));
                editRecord.setMessage(message);

                int count = 0;
                int SEUIL = 160;
                int SEUILAR = 70;
                int j = 1;

                final Pattern arabicLettersPattern = Pattern.compile("[\\u0600-\\u06FF\\u0750-\\u077F]",
                        Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE);

                final Matcher arabicLettersMatcher = arabicLettersPattern.matcher(message);

                //Counts each character except space    
                for (int i = 0; i < message.length(); i++) {
                    if (message.charAt(i) != ' ') {
                        count++;
                    }
                }

                editRecord.setLongeur(count);

                Matcher matcher = pattern.matcher(message);

                if (matcher.matches()) {
                    while (count > SEUIL) {
                        SEUIL = SEUIL + 160;
                        j++;
                    }
                }

                if (!matcher.matches()) {
                    while (count > SEUILAR) {
                        SEUILAR = SEUILAR + 70;
                        j++;
                    }
                }

                editRecord.setNmbreSms(j);

            }
            sessionMapObj.put("simBean", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        System.out.println("nom message est" + message);
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('simuGSMDialog').show();");

    }

    public static void editUserProfilRecordInDB(int Id) {
        AdminBean editRecord = null;
        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {

            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  UTILISATEUR,  SOLDE,  OPTIONU WHERE  UTILISATEUR.ID_UTILISATEUR=" + Id);

            if (resultSetObj != null) {
                resultSetObj.next();
                editRecord = new AdminBean();
                editRecord.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecord.setNom(resultSetObj.getString("NOM"));
                editRecord.setContactName(resultSetObj.getString("CONTACT_NAME"));
                editRecord.setContactGsm(resultSetObj.getString("CONTACT_GSM"));
                editRecord.setLogin(resultSetObj.getString("LOGIN"));
                editRecord.setPwd(resultSetObj.getString("PWD"));
                editRecord.setDateCreation(resultSetObj.getString("DATE_CREATION"));
                editRecord.setActivated(resultSetObj.getString("ACTIVATED"));

                editRecord.setIdSolde(resultSetObj.getInt("ID_SOLDE"));
                editRecord.setSolde(resultSetObj.getString("SOLDE"));

                editRecord.setIdOption(resultSetObj.getInt("ID_OPTION"));
                editRecord.setAr(resultSetObj.getBoolean("AR"));
                editRecord.setModifEntet(resultSetObj.getBoolean("MODIF_ENTET"));
                editRecord.setListParam(resultSetObj.getBoolean("LIST_PARAM"));
                editRecord.setDiffPlanifier(resultSetObj.getBoolean("DIFF_PLANIFIER"));

            }
            sessionMapObj.put("userBeanEdit", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('editProUserDialog').show();");
    }

    public static String editUserProfilNoRecordInDB(int Id) {
        AdminBean editRecord = null;

        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {

            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM  UTILISATEUR,  SOLDE WHERE  UTILISATEUR.ID_UTILISATEUR=" + Id);

            if (resultSetObj != null) {
                resultSetObj.next();
                editRecord = new AdminBean();
                editRecord.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecord.setNom(resultSetObj.getString("NOM"));
                editRecord.setContactName(resultSetObj.getString("CONTACT_NAME"));
                editRecord.setContactGsm(resultSetObj.getString("CONTACT_GSM"));
                editRecord.setLogin(resultSetObj.getString("LOGIN"));
                editRecord.setPwd(resultSetObj.getString("PWD"));
                editRecord.setDateCreation(resultSetObj.getString("DATE_CREATION"));
                editRecord.setActivated(resultSetObj.getString("ACTIVATED"));

                editRecord.setIdSolde(resultSetObj.getInt("ID_SOLDE"));
                editRecord.setSolde(resultSetObj.getString("SOLDE"));

            }
            sessionMapObj.put("userBeanEdit", editRecord);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }

        return "/GTWapp/EditUserProfilNo.xhtml?faces-redirect=true";
    }

    public static String updateAdminDetailsInDB(AdminBean updateAdminObj) {
        try {
            pstmt = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE.SOLDE =? WHERE  SOLDE.ID_UTILISATEUR =?");
            pstmt1 = getConnection().prepareStatement("UPDATE  UTILISATEUR SET  UTILISATEUR.NOM =?, UTILISATEUR.CONTACT_NAME =?,  UTILISATEUR.CONTACT_GSM =?,  UTILISATEUR.LOGIN =?,  UTILISATEUR.PWD =?,  UTILISATEUR.DATE_CREATION =?,  UTILISATEUR.ACTIVATED =? WHERE  UTILISATEUR.ID_UTILISATEUR = ?");

            pstmt.setString(1, updateAdminObj.getSolde());
            pstmt.setInt(2, updateAdminObj.getIdUtilisateur());

            pstmt1.setString(1, updateAdminObj.getNom());
            pstmt1.setString(2, updateAdminObj.getContactName());
            pstmt1.setString(3, updateAdminObj.getContactGsm());
            pstmt1.setString(4, updateAdminObj.getLogin());
            pstmt1.setString(5, updateAdminObj.getPwd());
            pstmt1.setString(6, updateAdminObj.getDateCreation());
            pstmt1.setString(7, updateAdminObj.getActivated());
            pstmt1.setInt(8, updateAdminObj.getIdUtilisateur());

            pstmt.executeUpdate();
            pstmt1.executeUpdate();

            new DatabaseOperation().hist_action("The admin with the name of " + updateAdminObj.getNom() + " and ID=" + updateAdminObj.getIdUtilisateur() + "is Updated by " + SessionUtils.getUserName());

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/ListAdmin.xhtml?faces-redirect=true";
    }

    public static void updateBlackListDetailsInDB(AdminBean updateAdminObj) {

        try {

            pstmt = getConnection().prepareStatement("UPDATE  BLACK_LIST SET GSM = ? WHERE ID_BLACK_LIST =?");

            pstmt.setString(1, updateAdminObj.getGsmblacklist());
            pstmt.setInt(2, updateAdminObj.getIdblacklist());

            pstmt.executeUpdate();
            new DatabaseOperation().hist_action("The user Update Black Liste number to " + updateAdminObj.getGsmblacklist());

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('BlackListDialog').show();");

    }

    public static void updateDetailsListGsmInDB(AdminBean updateAdminObj) throws SQLException {

        ResultSet idutSession, optionSession;
        int listparam = 0;
        int IDUSERSession = 0;

        stmtObj = getConnection().createStatement();
        System.out.println("GTWappOperation.DatabaseOperation.updateDetailsListGsmInDB()" + updateAdminObj.getIdDetailListGsm());

        idutSession = stmtObj.executeQuery("SELECT  ID_UTILISATEUR FROM   UTILISATEUR WHERE  LOGIN='" + SessionUtils.getUserName() + "'");
        while (idutSession.next()) {
            IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
        }

        optionSession = stmtObj.executeQuery("SELECT  LIST_PARAM FROM OPTIONU WHERE ID_UTILISATEUR=" + IDUSERSession);
        while (optionSession.next()) {
            listparam = optionSession.getInt("LIST_PARAM");
        }

        if (listparam == 1) {

            try {
                pstmt = getConnection().prepareStatement("UPDATE DETAIL_LIST_GSM SET GSM = '" + updateAdminObj.getGsm() + "', KEY1 = '" + updateAdminObj.getKey1() + "', KEY2 = '" + updateAdminObj.getKey2() + "', KEY3 = '" + updateAdminObj.getKey3() + "', KEY4 = '" + updateAdminObj.getKey4() + "', KEY5 = '" + updateAdminObj.getKey5() + "', KEY6 = '" + updateAdminObj.getKey6() + "' WHERE ID_DETAIL_LIST_GSM =?");
                pstmt.setInt(1, updateAdminObj.getIdDetailListGsm());

                pstmt.executeUpdate();

                connObj.close();
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
            }
        } else {
            try {
                pstmt = getConnection().prepareStatement("UPDATE DETAIL_LIST_GSM SET GSM = '" + updateAdminObj.getGsm() + "' WHERE ID_DETAIL_LIST_GSM=?");

                pstmt.setInt(1, updateAdminObj.getIdDetailListGsm());

                pstmt.executeUpdate();

                connObj.close();
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
            }
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('detailslistDialog').show();");

    }

    public static void saveListGSMDetailsInDB(AdminBean updateAdminObj) {
        try {
            pstmt = getConnection().prepareStatement("INSERT INTO DETAIL_LIST_GSM(ID_DETAIL_LIST_GSM, ID_LIST, GSM, KEY1, KEY2, KEY3, KEY4, KEY5, KEY6) VALUES(DETAIL_LIST_GSM_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, updateAdminObj.getIdList());
            pstmt.setString(2, updateAdminObj.getGsm());
            pstmt.setString(3, updateAdminObj.getKey1());
            pstmt.setString(4, updateAdminObj.getKey2());
            pstmt.setString(5, updateAdminObj.getKey3());
            pstmt.setString(6, updateAdminObj.getKey4());
            pstmt.setString(7, updateAdminObj.getKey5());
            pstmt.setString(8, updateAdminObj.getKey6());

            pstmt.executeUpdate();

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('detailslistDialog').show();");

    }

    public static String updateUserDetailsInDB(AdminBean updateAdminObj) {
        ResultSet idut;
        int IDUSER = 0;
        String NOMUSER = "";

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        ResultSet entetResult;
        String entetR = null;

        ResultSet histentetResult;
        ResultSet histentetResultadd;
        int saveResult1;
        int saveResult3;
        String histentetR;
        String histentetRadd = null;

        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR, UTILISATEUR.NOM FROM  UTILISATEUR WHERE  UTILISATEUR.ID_UTILISATEUR=" + updateAdminObj.getIdUtilisateur());
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
                NOMUSER = idut.getString("NOM");
            }

            pstmt = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE.SOLDE =? WHERE  SOLDE.ID_UTILISATEUR =?");
            pstmt1 = getConnection().prepareStatement("UPDATE  UTILISATEUR SET  UTILISATEUR.NOM =?, UTILISATEUR.CONTACT_NAME =?,  UTILISATEUR.CONTACT_GSM =?,  UTILISATEUR.LOGIN =?,  UTILISATEUR.PWD =?,  UTILISATEUR.DATE_CREATION =?,  UTILISATEUR.ACTIVATED =? WHERE  UTILISATEUR.ID_UTILISATEUR = ?");
            pstmt2 = getConnection().prepareStatement("UPDATE  OPTIONU SET  OPTIONU.AR =?,  OPTIONU.MODIF_ENTET =?,  OPTIONU.LIST_PARAM =?,  OPTIONU.DIFF_PLANIFIER =?,  OPTIONU.API =? WHERE  OPTIONU.ID_UTILISATEUR =?");
            //   pstmt3 = getConnection().prepareStatement("UPDATE ENTETE SET ENTETE = '" + updateAdminObj.getEntete() + "' WHERE ID_UTILISATEUR =" + IDUSER);

            entetResult = stmtObj.executeQuery("SELECT ENTETE FROM ENTETE WHERE ID_UTILISATEUR=" + IDUSER);
            while (entetResult.next()) {
                entetR = entetResult.getString("ENTETE");
            }

            System.out.println("GTWappOperation.DatabaseOperation.updateUserDetailsInDB()" + entetR);

            histentetResultadd = stmtObj.executeQuery("SELECT ENTETE_FINALE FROM HISTORIQUE_ENTETE WHERE  DATE_ENTETE IS NOT NULL AND ID_UTILISATEUR=" + IDUSER + " ORDER BY DATE_ENTETE ASC");
            while (histentetResultadd.next()) {
                histentetRadd = histentetResultadd.getString("ENTETE_FINALE");
            }

            histentetResult = stmtObj.executeQuery("SELECT * FROM HISTORIQUE_ENTETE WHERE ID_UTILISATEUR=" + IDUSER);

            if (histentetResult.next() == false) {
                pstmt4 = getConnection().prepareStatement("INSERT INTO HISTORIQUE_ENTETE (ID_HISTORIQUE_ENTETE, ID_UTILISATEUR, ENTETE_INITIALE, ENTETE_FINALE, DATE_ENTETE)VALUES (HISTORIQUE_ENTETE_SEQ.NEXTVAL," + IDUSER + ",'" + entetR + "', ?, ?)");
                pstmt4.setString(1, updateAdminObj.getEntete());
                pstmt4.setString(2, dateFormat.format(date));
                pstmt5 = getConnection().prepareStatement("UPDATE ENTETE SET ENTETE = '" + updateAdminObj.getEntete() + "' WHERE ENTETE.ID_UTILISATEUR =" + IDUSER);

                saveResult1 = pstmt4.executeUpdate();
                saveResult3 = pstmt5.executeUpdate();

            } else {
                pstmt6 = getConnection().prepareStatement("INSERT INTO HISTORIQUE_ENTETE ( ID_HISTORIQUE_ENTETE,ID_UTILISATEUR, ENTETE_INITIALE, ENTETE_FINALE, DATE_ENTETE)VALUES (HISTORIQUE_ENTETE_SEQ.NEXTVAL," + IDUSER + ",'" + histentetRadd + "',?, ?)");

                pstmt6.setString(1, updateAdminObj.getEntete());
                pstmt6.setString(2, dateFormat.format(date));

                pstmt7 = getConnection().prepareStatement("UPDATE ENTETE SET ENTETE = '" + updateAdminObj.getEntete() + "' WHERE ENTETE.ID_UTILISATEUR =" + IDUSER);

                saveResult1 = pstmt6.executeUpdate();
                saveResult3 = pstmt7.executeUpdate();

            }

            pstmt.setString(1, updateAdminObj.getSolde());
            pstmt.setInt(2, updateAdminObj.getIdUtilisateur());

            pstmt1.setString(1, updateAdminObj.getNom());
            pstmt1.setString(2, updateAdminObj.getContactName());
            pstmt1.setString(3, updateAdminObj.getContactGsm());
            pstmt1.setString(4, updateAdminObj.getLogin());
            pstmt1.setString(5, updateAdminObj.getPwd());
            pstmt1.setString(6, updateAdminObj.getDateCreation());
            pstmt1.setString(7, updateAdminObj.getActivated());
            pstmt1.setInt(8, updateAdminObj.getIdUtilisateur());

            pstmt2.setBoolean(1, updateAdminObj.getAr());
            pstmt2.setBoolean(2, updateAdminObj.getModifEntet());
            pstmt2.setBoolean(3, updateAdminObj.getListParam());
            pstmt2.setBoolean(4, updateAdminObj.getDiffPlanifier());
            pstmt2.setBoolean(5, updateAdminObj.getApi());

            pstmt2.setInt(6, updateAdminObj.getIdUtilisateur());

            pstmt.executeUpdate();
            pstmt1.executeUpdate();
            pstmt2.executeUpdate();
            // pstmt3.executeUpdate();
            new DatabaseOperation().hist_action("The user with the name of " + NOMUSER + " ID=" + IDUSER + " Updated by " + SessionUtils.getUserName());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/ListUser.xhtml?faces-redirect=true";
    }

    public static String updateUserProfilDetailsInDB(AdminBean updateAdminObj) {

        try {

            pstmt = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE.SOLDE =? WHERE  SOLDE.ID_UTILISATEUR =?");
            pstmt1 = getConnection().prepareStatement("UPDATE  UTILISATEUR SET  UTILISATEUR.NOM =?, UTILISATEUR.CONTACT_NAME =?,  UTILISATEUR.CONTACT_GSM =?,  UTILISATEUR.LOGIN =?,  UTILISATEUR.PWD =?,  UTILISATEUR.DATE_CREATION =?,  UTILISATEUR.ACTIVATED =? WHERE  UTILISATEUR.ID_UTILISATEUR = ?");

            pstmt.setString(1, updateAdminObj.getSolde());
            pstmt.setInt(2, updateAdminObj.getIdUtilisateur());

            pstmt1.setString(1, updateAdminObj.getNom());
            pstmt1.setString(2, updateAdminObj.getContactName());
            pstmt1.setString(3, updateAdminObj.getContactGsm());
            pstmt1.setString(4, updateAdminObj.getLogin());
            pstmt1.setString(5, updateAdminObj.getPwd());
            pstmt1.setString(6, updateAdminObj.getDateCreation());
            pstmt1.setString(7, updateAdminObj.getActivated());
            pstmt1.setInt(8, updateAdminObj.getIdUtilisateur());

            pstmt.executeUpdate();
            pstmt1.executeUpdate();
            new DatabaseOperation().hist_action("The user with the name of " + SessionUtils.getUserName() + " ID=" + updateAdminObj.getIdUtilisateur() + " Modify his profile ");
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/UserProfil.xhtml?faces-redirect=true";
    }

    public static String updateUserProfilNoDetailsInDB(AdminBean updateAdminObj) {
        try {
            pstmt = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE.SOLDE =? WHERE  SOLDE.ID_UTILISATEUR =?");
            pstmt1 = getConnection().prepareStatement("UPDATE  UTILISATEUR SET  UTILISATEUR.NOM =?, UTILISATEUR.CONTACT_NAME =?,  UTILISATEUR.CONTACT_GSM =?,  UTILISATEUR.LOGIN =?,  UTILISATEUR.PWD =?,  UTILISATEUR.DATE_CREATION =?,  UTILISATEUR.ACTIVATED =? WHERE  UTILISATEUR.ID_UTILISATEUR = ?");

            pstmt.setString(1, updateAdminObj.getSolde());
            pstmt.setInt(2, updateAdminObj.getIdUtilisateur());

            pstmt1.setString(1, updateAdminObj.getNom());
            pstmt1.setString(2, updateAdminObj.getContactName());
            pstmt1.setString(3, updateAdminObj.getContactGsm());
            pstmt1.setString(4, updateAdminObj.getLogin());
            pstmt1.setString(5, updateAdminObj.getPwd());
            pstmt1.setString(6, updateAdminObj.getDateCreation());
            pstmt1.setString(7, updateAdminObj.getActivated());
            pstmt1.setInt(8, updateAdminObj.getIdUtilisateur());

            pstmt.executeUpdate();
            pstmt1.executeUpdate();

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/UserProfilNo.xhtml?faces-redirect=true";
    }

    public static void editSoldeRecordInDB(int Ids) {
        AdminBean editRecords = null;

        Map<String, Object> sessionMapObjS = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmtObj = getConnection().createStatement();

            resultSetObj = stmtObj.executeQuery("SELECT * FROM  HISTORIQUE WHERE  HISTORIQUE.ID_UTILISATEUR=" + Ids + " ORDER BY  HISTORIQUE.DATE_ALIMENTATION DESC");
            if (resultSetObj != null) {
                resultSetObj.next();
                editRecords = new AdminBean();

                editRecords.setIdHistorique(resultSetObj.getInt("ID_HISTORIQUE"));
                editRecords.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecords.setSoldeInitial(resultSetObj.getInt("SOLDE_INITIAL"));
                editRecords.setSoldeFinal(0);
                editRecords.setDateAlimentation(resultSetObj.getString("DATE_ALIMENTATION"));

            }
            sessionMapObjS.put("adminBeanSolde", editRecords);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('alimentAdminDialog').show();");
    }

    public static void editSoldeUserRecordInDB(int Ids) {
        AdminBean editRecords = null;

        Map<String, Object> sessionMapObjS = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmtObj = getConnection().createStatement();

            resultSetObj = stmtObj.executeQuery("SELECT * FROM  HISTORIQUE WHERE  HISTORIQUE.ID_UTILISATEUR=" + Ids + " ORDER BY  HISTORIQUE.DATE_ALIMENTATION DESC");
            if (resultSetObj != null) {
                resultSetObj.next();
                editRecords = new AdminBean();

                editRecords.setIdHistorique(resultSetObj.getInt("ID_HISTORIQUE"));
                editRecords.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                editRecords.setSoldeInitial(resultSetObj.getInt("SOLDE_INITIAL"));
                editRecords.setSoldeFinal(0);
                editRecords.setDateAlimentation(resultSetObj.getString("DATE_ALIMENTATION"));

            }
            sessionMapObjS.put("userBeanSolde", editRecords);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('alimentAdminDialog').show();");
    }

    public static String updateSoldeInDB(AdminBean updateSoldeObj) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dat = dateFormat.format(date);

        ResultSet soldeResult;
        ArrayList IDUTList = new ArrayList();
        int soldeT = 0;
        int soldeI = 0;
        int soldeA = 0;
        int solde = 0;

        try {
            stmtObj = getConnection().createStatement();
            soldeResult = stmtObj.executeQuery("SELECT  HISTORIQUE.SOLDE_FINAL FROM  HISTORIQUE WHERE  HISTORIQUE.ID_UTILISATEUR = " + updateSoldeObj.getIdUtilisateur() + " ORDER BY  HISTORIQUE.DATE_ALIMENTATION ASC");
            while (soldeResult.next()) {
                soldeI = soldeResult.getInt("SOLDE_FINAL");
            }
            System.out.println("GTWappOperation.DatabaseOperation.updateSoldeInDB()" + soldeI);
            stmtObj = getConnection().createStatement();
            soldeResult = stmtObj.executeQuery("SELECT  SOLDE.SOLDE FROM  SOLDE WHERE  SOLDE.ID_UTILISATEUR=" + updateSoldeObj.getIdUtilisateur());
            while (soldeResult.next()) {
                soldeA = soldeResult.getInt("SOLDE");
            }
            System.out.println("GTWappOperation.DatabaseOperation.updateSoldeInDB()  " + soldeA);

            if (soldeA == soldeI) {
                solde = soldeI + updateSoldeObj.getSoldeFinal();
            } else {
                solde = soldeA + updateSoldeObj.getSoldeFinal();
            }
            System.out.println("GTWappOperati" + solde);
            stmtObj = getConnection().createStatement();

            soldeResult = stmtObj.executeQuery("SELECT  SOLDE.SOLDE FROM  SOLDE WHERE  SOLDE.ID_UTILISATEUR =" + updateSoldeObj.getIdUtilisateur() + "ORDER BY  SOLDE.ID_SOLDE DESC");
            while (soldeResult.next()) {
                soldeT = soldeResult.getInt("SOLDE");
            }
            System.out.println("GTWappOperation.DatabaseOperation.updateSoldeInDB()" + updateSoldeObj.getIdUtilisateur());
            pstmt = getConnection().prepareStatement("INSERT INTO HISTORIQUE(ID_HISTORIQUE , ID_UTILISATEUR , SOLDE_INITIAL , SOLDE_FINAL , DATE_ALIMENTATION) VALUES(HISTORIQUE_SEQ.NEXTVAL," + updateSoldeObj.getIdUtilisateur() + "," + soldeT + "," + solde + ",'" + dat + "')");

            pstmt1 = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE.SOLDE =" + solde + " WHERE  SOLDE.ID_UTILISATEUR =" + updateSoldeObj.getIdUtilisateur());

            pstmt.executeUpdate();
            pstmt1.executeUpdate();
            new DatabaseOperation().hist_action("Solde alimented from" + soldeA + "to" + solde + "by" + SessionUtils.getUserName());

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/ListeSolde.xhtml?faces-redirect=true";
    }

    public static String updateSoldeUserInDB(AdminBean updateSoldeObj) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dat = dateFormat.format(date);

        ResultSet soldeResult;
        ArrayList IDUTList = new ArrayList();

        ResultSet idut;
        int IDUSER = 0;
        String NOMUSER = "";
        int soldeT = 0;
        int soldeI = 0;
        int soldeA = 0;
        int idAdmin = 0;

        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR, UTILISATEUR.NOM FROM  UTILISATEUR WHERE  UTILISATEUR.ID_UTILISATEUR=" + updateSoldeObj.getIdUtilisateur());
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
                NOMUSER = idut.getString("NOM");
            }

            soldeResult = stmtObj.executeQuery("SELECT  HISTORIQUE.SOLDE_FINAL FROM  HISTORIQUE WHERE  HISTORIQUE.ID_UTILISATEUR = " + updateSoldeObj.getIdUtilisateur() + " ORDER BY  HISTORIQUE.DATE_ALIMENTATION ASC");
            while (soldeResult.next()) {
                soldeI = soldeResult.getInt("SOLDE_FINAL");
            }

            int solde = soldeI + updateSoldeObj.getSoldeFinal();

            stmtObj = getConnection().createStatement();
            soldeResult = stmtObj.executeQuery("SELECT  PARENT.ID_ADMIN,  SOLDE.SOLDE FROM  PARENT, SOLDE WHERE  PARENT.ID_CHILD=" + updateSoldeObj.getIdUtilisateur() + " AND  PARENT.ID_ADMIN= SOLDE.ID_UTILISATEUR");
            while (soldeResult.next()) {
                soldeA = soldeResult.getInt("SOLDE");
                idAdmin = soldeResult.getInt("ID_ADMIN");
            }

            int soldeAUpdate = soldeA - updateSoldeObj.getSoldeFinal();

            stmtObj = getConnection().createStatement();
            soldeResult = stmtObj.executeQuery("SELECT  SOLDE.SOLDE FROM  SOLDE WHERE  SOLDE.ID_UTILISATEUR =" + updateSoldeObj.getIdUtilisateur() + "ORDER BY  SOLDE.ID_SOLDE DESC");
            while (soldeResult.next()) {
                soldeT = soldeResult.getInt("SOLDE");
            }
            pstmt = getConnection().prepareStatement("INSERT INTO HISTORIQUE(ID_HISTORIQUE , ID_UTILISATEUR , SOLDE_INITIAL , SOLDE_FINAL , DATE_ALIMENTATION) VALUES(HISTORIQUE_SEQ.NEXTVAL," + updateSoldeObj.getIdUtilisateur() + "," + soldeT + "," + solde + ",'" + dat + "')");

            pstmt1 = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE.SOLDE =" + solde + " WHERE  SOLDE.ID_UTILISATEUR =" + updateSoldeObj.getIdUtilisateur());
            pstmt2 = getConnection().prepareStatement("UPDATE  SOLDE SET  SOLDE.SOLDE =" + soldeAUpdate + " WHERE  SOLDE.ID_UTILISATEUR =" + idAdmin);

            pstmt.executeUpdate();
            pstmt1.executeUpdate();
            pstmt2.executeUpdate();

            new DatabaseOperation().hist_action("Solde alimented from " + soldeT + " to " + solde + " by " + SessionUtils.getUserName() + " for " + NOMUSER + "ID=" + IDUSER);
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/ListSoldeUser.xhtml?faces-redirect=true";
    }

    public static String desactiveAdminRecordInDB(int Id) {

        ResultSet idutSession;

        int IDUSERSession = 0;
        String NOMUSER = "";

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR,  UTILISATEUR.NOM FROM  UTILISATEUR WHERE  UTILISATEUR.ID_UTILISATEUR=" + Id);
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
                NOMUSER = idutSession.getString("NOM");
            }

            pstmt = getConnection().prepareStatement("UPDATE  UTILISATEUR SET  UTILISATEUR.ACTIVATED ='False' WHERE  UTILISATEUR.ID_UTILISATEUR =" + Id);

            pstmt.executeUpdate();
            new DatabaseOperation().hist_action("The admin with the name of " + NOMUSER + " and ID =" + IDUSERSession + " desactivated by " + SessionUtils.getUserName());

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/ListAdmin.xhtml?faces-redirect=true";
    }

    public static String desactiveUserRecordInDB(int Id) {
        ResultSet idutSession;

        int IDUSERSession = 0;
        String NOMUSER = "";

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR,  UTILISATEUR.NOM FROM  UTILISATEUR WHERE  UTILISATEUR.ID_UTILISATEUR=" + Id);
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
                NOMUSER = idutSession.getString("NOM");
            }
            pstmt = getConnection().prepareStatement("UPDATE  UTILISATEUR SET  UTILISATEUR.ACTIVATED ='False' WHERE  UTILISATEUR.ID_UTILISATEUR =" + Id);

            pstmt.executeUpdate();
            new DatabaseOperation().hist_action("The user with the name of " + NOMUSER + " and ID =" + IDUSERSession + " desactivated by " + SessionUtils.getUserName());

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/ListUser.xhtml?faces-redirect=true";
    }

    public static String ActiveAdminRecordInDB(int Id) {

        ResultSet idutSession;

        int IDUSERSession = 0;
        String NOMUSER = "";

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR,  UTILISATEUR.NOM FROM  UTILISATEUR WHERE  UTILISATEUR.ID_UTILISATEUR=" + Id);
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
                NOMUSER = idutSession.getString("NOM");
            }
            pstmt = getConnection().prepareStatement("UPDATE  UTILISATEUR SET  UTILISATEUR.ACTIVATED ='True' WHERE  UTILISATEUR.ID_UTILISATEUR =" + Id);

            pstmt.executeUpdate();
            new DatabaseOperation().hist_action("The admin with the name of " + NOMUSER + " and ID =" + IDUSERSession + " Activated by " + SessionUtils.getUserName());

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/ListAdmin.xhtml?faces-redirect=true";
    }

    public static String ActiveUserRecordInDB(int Id) {

        ResultSet idutSession;

        int IDUSERSession = 0;
        String NOMUSER = "";

        try {

            stmtObj = getConnection().createStatement();

            idutSession = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR,  UTILISATEUR.NOM FROM  UTILISATEUR WHERE  UTILISATEUR.ID_UTILISATEUR=" + Id);
            while (idutSession.next()) {
                IDUSERSession = idutSession.getInt("ID_UTILISATEUR");
                NOMUSER = idutSession.getString("NOM");
            }

            pstmt = getConnection().prepareStatement("UPDATE  UTILISATEUR SET  UTILISATEUR.ACTIVATED ='True' WHERE  UTILISATEUR.ID_UTILISATEUR =" + Id);

            pstmt.executeUpdate();
            new DatabaseOperation().hist_action("The admin with the name of " + NOMUSER + " and ID =" + IDUSERSession + " Activated by " + SessionUtils.getUserName());

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/ListUser.xhtml?faces-redirect=true";
    }

    public static ArrayList getNomListFromDB() {
        ArrayList NomList = new ArrayList();

        int IDUSER = 0;
        ResultSet idut;

        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            resultSetObj = stmtObj.executeQuery("SELECT * FROM  LISTE_GSM WHERE  LISTE_GSM.ID_UTILISATEUR=" + IDUSER);
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setIdListeGsm(resultSetObj.getInt("ID_LISTE_GSM"));
                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setNomListe(resultSetObj.getString("NOM_LISTE"));
                AdminObj.setDateCreationListe(resultSetObj.getString("DATE_CREATION_LISTE"));
                AdminObj.setParametrable(resultSetObj.getString("PARAMETRABLE"));

                NomList.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return NomList;
    }

    public static ArrayList getNomMSGFromDB() {
        ArrayList NomMSG = new ArrayList();

        int IDUSER = 0;
        ResultSet idut;

        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
            }

            resultSetObj = stmtObj.executeQuery("SELECT * FROM  MESSAGE WHERE  MESSAGE.ID_UTILISATEUR=" + IDUSER);
            while (resultSetObj.next()) {

                AdminBean AdminObj = new AdminBean();
                AdminObj.setIdMessage(resultSetObj.getInt("ID_MESSAGE"));
                AdminObj.setIdUtilisateur(resultSetObj.getInt("ID_UTILISATEUR"));
                AdminObj.setNomMessage(resultSetObj.getString("NOM_MESSAGE"));
                AdminObj.setMessage(resultSetObj.getString("MESSAGE"));
                AdminObj.setDateCreationMessage(resultSetObj.getString("DATE_CREATION_MESSAGE"));
                AdminObj.setLongeur(resultSetObj.getInt("LONGEUR"));
                AdminObj.setNmbreSms(resultSetObj.getInt("NMBRE_SMS"));
                NomMSG.add(AdminObj);

            }

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return NomMSG;
    }

    public void hist_action(String action) {

        int IDUSER = 0;
        String NOMUSER = "";
        ResultSet idut;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dat = dateFormat.format(date);

        try {
            stmtObj = getConnection().createStatement();

            idut = stmtObj.executeQuery("SELECT  UTILISATEUR.ID_UTILISATEUR,  UTILISATEUR.NOM FROM  UTILISATEUR WHERE  UTILISATEUR.LOGIN='" + SessionUtils.getUserName() + "'");
            while (idut.next()) {
                IDUSER = idut.getInt("ID_UTILISATEUR");
                NOMUSER = idut.getString("NOM");
            }

            pstmt = getConnection().prepareStatement("INSERT INTO  HIST_ACTION (ID_UTILISATEUR, NOM, ACTION, TIME) VALUES (" + IDUSER + ",'" + NOMUSER + "' , '" + action + "','" + dat + "')");

            System.out.println("GTWappOperation.DatabaseOperation.hist_action()" + IDUSER + "" + NOMUSER);

            pstmt.executeUpdate();

            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

}
