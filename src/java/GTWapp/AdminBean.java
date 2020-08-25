/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTWapp;

/**
 *
 * @author wael
 */
import GTWappOperation.DatabaseOperation;
import static GTWappOperation.DatabaseOperation.getConnection;
import static GTWappOperation.DatabaseOperation.stmtObj;
import controller.util.SessionUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;
import org.primefaces.PrimeFaces;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

@ManagedBean
@RequestScoped
public class AdminBean implements Serializable {

    private PieChartModel pieModel;
    private LineChartModel dateModel;
    private boolean validGsm;

    private int ids;
    private int idSolde;
    private int idUtilisateur;
    private String solde;
    private String nom;
    private String contactName;
    private String contactGsm;
    private String login;
    private String pwd;
    private String dateCreation;
    private String activated;

    private int idHistorique;
    private int soldeInitial;
    private int soldeFinal;
    private String dateAlimentation;

    private int idRole;
    private String role;

    private int idParent;
    private int idChild;
    private int idAdmin;

    private int idOption;
    private Boolean ar;
    private Boolean modifEntet;
    private Boolean listParam;
    private Boolean diffPlanifier;
    private Boolean api;
    private String option;

    private int idHistoriqueEntete;
    private String enteteInitiale;
    private String enteteFinale;
    private String dateEntete;

    private int idEntete;
    private String entete;

    private int idMessage;
    private String nomMessage;
    private String message;
    private String dateCreationMessage;
    private int longeur;
    private int nmbreSms;

    private int idListeGsm;
    private String nomListe;
    private String dateCreationListe;
    private String parametrable;

    private int idDetailListGsm;
    private int idList;
    private String gsm;
    private String key1;
    private String key2;
    private String key3;
    private String key4;
    private String key5;
    private String key6;

    private int idHistDiffusion;
    private String smsid;
    private String status;

    private int idDiffusion;
    private int idMessageDiffusion;
    private int idListDiffusion;
    private String dateDiffusionCreation;
    private String nomCompagneDiffusion;
    private String enteteDiffusion;
    private String dateEnvoie;
    private String planifie;

    private int idblacklist;
    private String gsmblacklist;

    private static String DateFinLineChart;
    private static String DateDebutLineChart;

    private static Object lastelement = null;
    private static Date getStartDt;

    private static int Rejected = 0;
    private static int Delivred = 0;
    private static int Waiting = 0;
    private static LineChartSeries series1 = new LineChartSeries();

    private Date date10;
    private Date datetimedebut;
    private Date datetimfin;
    public ArrayList adminListFromDB;
    public ArrayList userProfilFromDB;
    public ArrayList userProfilNoFromDB;
    public ArrayList userListFromDB;
    public ArrayList HistoriqueListFromDB;
    public ArrayList adminFromDB;
    public ArrayList userFromDB;
    public ArrayList MessageListFromDB;
    public ArrayList GSMListFromDB;
    public ArrayList DetailGSMListFromDB;
    public ArrayList NomListFromDB;
    public ArrayList NomMSGFromDB;

    public ArrayList HistoriqueEntetListFromDB;
    public ArrayList DiffCompDisplayListFromDB;
    public ArrayList BlackListListFromDB;

    private List<AdminBean> filteredAdminList;
    private List<AdminBean> filteredSoldeList;
    private List<AdminBean> filteredHistList;
    private List<AdminBean> filteredHistDiffTable;
    private UploadedFile file;

    private Part uploadedFile;
    private String folder = "C:\\Users\\Administrateur\\Desktop\\gtew";
    private String fileName = " ";

    private String[] selectedConsoles;

    public String[] getSelectedConsoles() {
        return selectedConsoles;
    }

    public void setSelectedConsoles(String[] selectedConsoles) {
        this.selectedConsoles = selectedConsoles;
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void saveFile() {

        try (InputStream input = uploadedFile.getInputStream()) {
            String fileName = uploadedFile.getSubmittedFileName();

            Files.copy(input, new File(folder, fileName).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDate10(Date date10) {
        this.date10 = date10;
    }

    public Date getDate10() {
        return date10;
    }

    public void setDatetimedebut(Date datetimedebut) {
        this.datetimedebut = datetimedebut;
    }

    public Date getDatetimedebut() {
        return datetimedebut;
    }

    public void setDatetimfin(Date datetimfin) {
        this.datetimfin = datetimfin;
    }

    public Date getDatetimfin() {
        return datetimfin;
    }

    public int getIdDetailListGsm() {
        return idDetailListGsm;
    }

    public void setIdDetailListGsm(int idDetailListGsm) {
        this.idDetailListGsm = idDetailListGsm;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public int getIdList() {
        return idList;
    }

    public void setIdList(int idList) {
        this.idList = idList;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGsmblacklist() {
        return gsmblacklist;
    }

    public void setGsmblacklist(String gsmblacklist) {
        this.gsmblacklist = gsmblacklist;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4;
    }

    public String getKey5() {
        return key5;
    }

    public void setKey5(String key5) {
        this.key5 = key5;
    }

    public String getKey6() {
        return key6;
    }

    public void setKey6(String key6) {
        this.key6 = key6;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdblacklist() {
        return idblacklist;
    }

    public void setIdblacklist(int idblacklist) {
        this.idblacklist = idblacklist;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactGsm() {
        return contactGsm;
    }

    public void setContactGsm(String contactGsm) {
        this.contactGsm = contactGsm;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public int getIdSolde() {
        return idSolde;
    }

    public void setIdSolde(int idSolde) {
        this.idSolde = idSolde;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public int getIdHistorique() {
        return idHistorique;
    }

    public void setIdHistorique(int idHistorique) {
        this.idHistorique = idHistorique;
    }

    public int getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(int soldeInitial) {
        this.soldeInitial = soldeInitial;
    }

    public int getSoldeFinal() {
        return soldeFinal;
    }

    public void setSoldeFinal(int soldeFinal) {
        this.soldeFinal = soldeFinal;
    }

    public String getDateAlimentation() {
        return dateAlimentation;
    }

    public void setDateAlimentation(String dateAlimentation) {
        this.dateAlimentation = dateAlimentation;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }

    public int getIdChild() {
        return idChild;
    }

    public void setIdChild(int idChild) {
        this.idChild = idChild;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdOption() {
        return idOption;
    }

    public void setIdOption(int idOption) {
        this.idOption = idOption;
    }

    public Boolean getAr() {
        return ar;
    }

    public void setAr(Boolean ar) {
        this.ar = ar;
    }

    public Boolean getModifEntet() {
        return modifEntet;
    }

    public void setModifEntet(Boolean modifEntet) {
        this.modifEntet = modifEntet;
    }

    public Boolean getListParam() {
        return listParam;
    }

    public void setListParam(Boolean listParam) {
        this.listParam = listParam;
    }

    public Boolean getDiffPlanifier() {
        return diffPlanifier;
    }

    public void setDiffPlanifier(Boolean diffPlanifier) {
        this.diffPlanifier = diffPlanifier;
    }

    public Boolean getApi() {
        return api;
    }

    public void setApi(Boolean api) {
        this.api = diffPlanifier;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getIdHistoriqueEntete() {
        return idHistoriqueEntete;
    }

    public void setIdHistoriqueEntete(int idHistoriqueEntete) {
        this.idHistoriqueEntete = idHistoriqueEntete;
    }

    public String getEnteteInitiale() {
        return enteteInitiale;
    }

    public void setEnteteInitiale(String enteteInitiale) {
        this.enteteInitiale = enteteInitiale;
    }

    public String getEnteteFinale() {
        return enteteFinale;
    }

    public void setEnteteFinale(String enteteFinale) {
        this.enteteFinale = enteteFinale;
    }

    public String getDateEntete() {
        return dateEntete;
    }

    public void setDateEntete(String dateEntete) {
        this.dateEntete = dateEntete;
    }

    public int getIdEntete() {
        return idEntete;
    }

    public void setIdEntete(int idEntete) {
        this.idEntete = idEntete;
    }

    public String getEntete() {
        return entete;
    }

    public void setEntete(String entete) {
        this.entete = entete;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getNomMessage() {
        return nomMessage;
    }

    public void setNomMessage(String nomMessage) {
        this.nomMessage = nomMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateCreationMessage() {
        return dateCreationMessage;
    }

    public void setDateCreationMessage(String dateCreationMessage) {
        this.dateCreationMessage = dateCreationMessage;
    }

    public int getLongeur() {
        return longeur;
    }

    public void setLongeur(int longeur) {
        this.longeur = longeur;
    }

    public int getNmbreSms() {
        return nmbreSms;
    }

    public void setNmbreSms(int nmbreSms) {
        this.nmbreSms = nmbreSms;
    }

    public int getIdListeGsm() {
        return idListeGsm;
    }

    public void setIdListeGsm(int idListeGsm) {
        this.idListeGsm = idListeGsm;
    }

    public String getNomListe() {
        return nomListe;
    }

    public void setNomListe(String nomListe) {
        this.nomListe = nomListe;
    }

    public String getDateCreationListe() {
        return dateCreationListe;
    }

    public void setDateCreationListe(String dateCreationListe) {
        this.dateCreationListe = dateCreationListe;
    }

    public String getParametrable() {
        return parametrable;
    }

    public void setParametrable(String parametrable) {
        this.parametrable = parametrable;
    }

    public int getIdHistDiffusion() {
        return idHistDiffusion;
    }

    public void setIdHistDiffusion(int idHistDiffusion) {
        this.idHistDiffusion = idHistDiffusion;
    }

    public String getSmsid() {
        return smsid;
    }

    public void setSmsid(String smsid) {
        this.smsid = smsid;
    }

    public int getIdDiffusion() {
        return idDiffusion;
    }

    public void setIdDiffusion(int idDiffusion) {
        this.idDiffusion = idDiffusion;
    }

    public int getIdMessageDiffusion() {
        return idMessageDiffusion;
    }

    public void setIdMessageDiffusion(int idMessageDiffusion) {
        this.idMessageDiffusion = idMessageDiffusion;
    }

    public int getIdListDiffusion() {
        return idListDiffusion;
    }

    public void setIdListDiffusion(int idListDiffusion) {
        this.idListDiffusion = idListDiffusion;
    }

    public String getDateDiffusionCreation() {
        return dateDiffusionCreation;
    }

    public void setDateDiffusionCreation(String dateDiffusionCreation) {
        this.dateDiffusionCreation = dateDiffusionCreation;
    }

    public String getNomCompagneDiffusion() {
        return nomCompagneDiffusion;
    }

    public void setNomCompagneDiffusion(String nomCompagneDiffusion) {
        this.nomCompagneDiffusion = nomCompagneDiffusion;
    }

    public String getEnteteDiffusion() {
        return enteteDiffusion;
    }

    public void setEnteteDiffusion(String enteteDiffusion) {
        this.enteteDiffusion = enteteDiffusion;
    }

    public String getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(String dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    public String getPlanifie() {
        return planifie;
    }

    public void setPlanifie(String planifie) {
        this.planifie = planifie;
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    public void setDateModel() {
        this.dateModel = dateModel;
    }

    public ArrayList adminList() {
        return adminListFromDB;
    }

    public ArrayList userList() {
        return userListFromDB;
    }

    public ArrayList userProfil() {
        return userProfilFromDB;
    }

    public ArrayList userProfilNo() {
        return userProfilNoFromDB;
    }

    public ArrayList getNomListGsm() {
        return NomListFromDB;
    }

    public ArrayList getListNomMessage() {
        return NomMSGFromDB;
    }

    public ArrayList BlackListisplay() {
        return BlackListListFromDB;
    }

    public ArrayList HistoriqueEntet(int idUtilisateur) {
        return DatabaseOperation.showEntet(idUtilisateur);
    }

    public ArrayList DiffCompDisplay(int idUtilisateur) {
        return DatabaseOperation.DiffCompList(idUtilisateur);
    }

    public ArrayList ListMessages(int idUtilisateur) {

        return DatabaseOperation.ListMessagesFromDb(idUtilisateur);

    }

    public ArrayList ListHistoriqueDiff(AdminBean newAdminObj) {

        return DatabaseOperation.ListHistoriqueDiffOld(newAdminObj);
    }

    public ArrayList ListHistoriqueAPI(AdminBean newAdminObj) {

        return DatabaseOperation.ListHistoriqueAPI(newAdminObj);
    }

    public ArrayList ListGsm() {
        return DatabaseOperation.ListGSMFromDb();
    }

    public ArrayList ListCompagne() {
        return DatabaseOperation.ListCompagneFromDB();
    }

    public ArrayList ListCompagneAPI() {
        return DatabaseOperation.ListCompagneAPIFromDB();
    }

    public ArrayList getCompagneDiffusionList() {
        return DatabaseOperation.CompagneDiffusionListFromDb();
    }

    public ArrayList DetailsListGsm(int idUtilisateur) {
        return DatabaseOperation.DetailsListGSMFromDb(idUtilisateur);
    }

    public ArrayList DetailsListCompagne(int idUtilisateur) {
        return DatabaseOperation.DetailsListCompagneFromDb(idUtilisateur);
    }

    public ArrayList DetailsListCompagneAPI(int idUtilisateur) {
        return DatabaseOperation.DetailsListCompagneApiFromDb(idUtilisateur);
    }

    public ArrayList HistoriqueList(int idUtilisateur) {

        return DatabaseOperation.showHist(idUtilisateur);
    }

    public ArrayList AdminByIDList(int idUtilisateur) {
        return DatabaseOperation.showAdminByID(idUtilisateur);
    }

    public ArrayList UserByIDList(int idUtilisateur) {
        return DatabaseOperation.showUserByID(idUtilisateur);
    }

    public String saveAdminDetails(AdminBean newAdminObj) {
        return DatabaseOperation.saveAdminDetailsInDB(newAdminObj);
    }

    public void saveGSMDetails(AdminBean newAdminObj) {
        System.out.println("GTWapp.AdminBean.saveGSMDetails() 99999999999");

        int saveResult = DatabaseOperation.saveGSMDetailsInDB(newAdminObj);
        System.out.println("7777" + saveResult);

        PrimeFaces current = PrimeFaces.current();

        current.executeScript("PF('confirmDialogwidgetyes').show();");
    }

    public String saveUserDetails(AdminBean newAdminObj) throws SQLException {
        return DatabaseOperation.saveUserDetailsInDB(newAdminObj);
    }

    public void saveUserMSGDetails(AdminBean newAdminObj) {
        DatabaseOperation.saveUserMessagesInDB(newAdminObj);
    }

    public void sendMSGDetails(AdminBean newAdminObj) {
        DatabaseOperation.sendMessagesInDB(newAdminObj);
    }

    public String saveUserEntet(AdminBean newAdminObj) {
        return DatabaseOperation.saveEntetInDB(newAdminObj);
    }

    public String saveDiffComp(AdminBean newAdminObj) {
        return DatabaseOperation.saveDiffCompInDB(newAdminObj);
    }

    public String saveDiffCompPlan(AdminBean newAdminObj) {
        return DatabaseOperation.saveDiffCompPlaInDB(newAdminObj);
    }

    public String ifParam() {
        return DatabaseOperation.ifParamInDB();
    }

    public String ifdiffPlanifier() {
        return DatabaseOperation.ifdiffPlanifierInDB();
    }

    public String ifAPI() {
        return DatabaseOperation.ifAPIInDB();
    }

    public String ifmodifentet() {
        return DatabaseOperation.ifmodifentetInDB();
    }

    public String GetNomCompagne(int id) {
        return DatabaseOperation.GetNomCompagneInDB(id);
    }

    public void saveUserGSM(AdminBean newAdminObj) throws IOException {

        try (InputStream input = uploadedFile.getInputStream()) {
            fileName = uploadedFile.getSubmittedFileName();
            System.out.println("GTWapp.AdminBean.saveUserGSM()" + fileName);

            Files.copy(input, new File(folder, fileName).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatabaseOperation.saveGSMInDB(newAdminObj, folder + fileName);

        Files.deleteIfExists(Paths.get(folder + fileName));
        System.out.println("File Deleted");
    }

    public void saveBKList() throws IOException, FileNotFoundException, SQLException {

        try (InputStream input = uploadedFile.getInputStream()) {
            fileName = uploadedFile.getSubmittedFileName();

            Files.copy(input, new File(folder, fileName).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        validGsm = DatabaseOperation.saveEXLBKInDB(folder + fileName);
        System.out.println("sauvegarde" + validGsm);

        if (validGsm == true) {

            /* ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());*/
            Files.deleteIfExists(Paths.get(folder + fileName));
            System.out.println("File Deleted");
            PrimeFaces current = PrimeFaces.current();

            current.executeScript("PF('confirmDialogwidgetyes').show();");

        } else {
            
              Files.deleteIfExists(Paths.get(folder + fileName));
            System.out.println("File Deleted");
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('confirmDialogwidgetno').show();");

        }

    }

    public void reloadBKList() {

        System.out.println("GTWapp.AdminBean.reloadBKList()");
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('BlackListDialog').show();");

    }

    public void saveListGSM(int id) throws IOException, FileNotFoundException, SQLException {

        try (InputStream input = uploadedFile.getInputStream()) {
            fileName = uploadedFile.getSubmittedFileName();

            Files.copy(input, new File(folder, fileName).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // current.executeScript("PF('detailslistDialog').show();");
        validGsm = DatabaseOperation.saveEXLInDB(folder + fileName, id);
        System.out.println("sauvegarde" + validGsm);

        if (validGsm == true) {

            /* ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());*/
            Files.deleteIfExists(Paths.get(folder + fileName));
            System.out.println("File Deleted");

            PrimeFaces current = PrimeFaces.current();

            current.executeScript("PF('confirmDetaulsDialogwidgetyes').show();");

        } else {
            Files.deleteIfExists(Paths.get(folder + fileName));
            System.out.println("File Deleted");
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('confirmlistDialogwidgetno').show();");

        }

    }

    public void editBlackList(int idUtilisateur) {
        DatabaseOperation.editBlackListRecordInDB(idUtilisateur);
    }

    public void saveOneGsmList(int idUtilisateur) {
        DatabaseOperation.savesaveOneGsmListRecordInDB(idUtilisateur);
    }

    public void editDetailListGsm(int idUtilisateur) {
        DatabaseOperation.editDetailListGsmRecordInDB(idUtilisateur);
    }

    public void displaybuttonGsm(int idUtilisateur) {
        DatabaseOperation.DisplaybuttonRecordInDB(idUtilisateur);
    }

    public void displaybuttonHistComp(int idUtilisateur) {
        DatabaseOperation.DisplaybuttonHistCompRecordInDB(idUtilisateur);
    }

    public void displaybuttonHistCompAPI(AdminBean newAdminObj) {
        DatabaseOperation.DisplaybuttonHistCompAPIRecordInDB(newAdminObj);
    }

    public void deleteBlackList(int idUtilisateur) {
        DatabaseOperation.DeleteBlackListInDB(idUtilisateur);
    }

    public void deleteDetListGsm(int idUtilisateur) {
        DatabaseOperation.DeleteDetListGsmInDB(idUtilisateur);
    }

    public void editAdmin(int idUtilisateur) {
        DatabaseOperation.editAdminRecordInDB(idUtilisateur);
    }

    public void editUser(int idUtilisateur) {
        DatabaseOperation.editUserRecordInDB(idUtilisateur);
    }

    public void SimulateMob(String nomMessage) {
        DatabaseOperation.SimulateMobInDB(nomMessage);
    }

    public void SimReaMob(String nomMessage) {
        DatabaseOperation.SimReaMobInDB(nomMessage);
    }

    public void editProfilUser(int idUtilisateur) {
        DatabaseOperation.editUserProfilRecordInDB(idUtilisateur);
    }

    public String editProfilUserNo(int idUtilisateur) {
        return DatabaseOperation.editUserProfilNoRecordInDB(idUtilisateur);
    }

    public String updateAdminDetails(AdminBean updateAdminObj) {

        return DatabaseOperation.updateAdminDetailsInDB(updateAdminObj);
    }

    public void updateBlackListDetails(AdminBean updateAdminObj) {
        DatabaseOperation.updateBlackListDetailsInDB(updateAdminObj);
    }

    public void updateDetailsListGsm(AdminBean updateAdminObj) throws SQLException {
        DatabaseOperation.updateDetailsListGsmInDB(updateAdminObj);
    }

    public void saveListGSMDetails(AdminBean updateAdminObj) {
        DatabaseOperation.saveListGSMDetailsInDB(updateAdminObj);
    }

    public void deleteBlackListDetails(AdminBean updateAdminObj) {
        DatabaseOperation.deleteBlackListRecordInDB(updateAdminObj);
    }

    public void deleteGSMListDetails(AdminBean updateAdminObj) {
        DatabaseOperation.deleteGSMListDetailsRecordInDB(updateAdminObj);
    }

    public String updateUserDetails(AdminBean updateAdminObj) {
        return DatabaseOperation.updateUserDetailsInDB(updateAdminObj);
    }

    public String updateUserProfilDetails(AdminBean updateAdminObj) {
        return DatabaseOperation.updateUserProfilDetailsInDB(updateAdminObj);
    }

    public String updateUserProfilNoDetails(AdminBean updateAdminObj) {
        return DatabaseOperation.updateUserProfilNoDetailsInDB(updateAdminObj);
    }

    public void Alimentation(int idUtilisateur) {
        DatabaseOperation.editSoldeRecordInDB(idUtilisateur);
    }

    public void AlimentationUser(int idUtilisateur) {
        DatabaseOperation.editSoldeUserRecordInDB(idUtilisateur);
    }

    public String updateSold(AdminBean updateSoldeObj) {
        return DatabaseOperation.updateSoldeInDB(updateSoldeObj);
    }

    public String updateSoldUser(AdminBean updateSoldeObj) {
        return DatabaseOperation.updateSoldeUserInDB(updateSoldeObj);
    }

    public String desactiveAdminRecord(int idUtilisateur) {
        return DatabaseOperation.desactiveAdminRecordInDB(idUtilisateur);
    }

    public String ActiveAdminRecord(int idUtilisateur) {
        return DatabaseOperation.ActiveAdminRecordInDB(idUtilisateur);
    }

    public String desactiveUserRecord(int idUtilisateur) {
        return DatabaseOperation.desactiveUserRecordInDB(idUtilisateur);
    }

    public String ActiveUserRecord(int idUtilisateur) {
        return DatabaseOperation.ActiveUserRecordInDB(idUtilisateur);
    }

    public List<AdminBean> getFilteredAdminList() {
        return filteredAdminList;
    }

    public void setFilteredAdminList(List<AdminBean> filteredAdminList) {
        this.filteredAdminList = filteredAdminList;
    }

    public List<AdminBean> getFilteredSoldeList() {
        return filteredSoldeList;
    }

    public void setFilteredSoldeList(List<AdminBean> filteredSoldeList) {
        this.filteredSoldeList = filteredSoldeList;
    }

    public List<AdminBean> getFilteredHistList() {
        return filteredHistList;
    }

    public void setFilteredHistList(List<AdminBean> filteredHistList) {
        this.filteredHistList = filteredHistList;
    }

    public List<AdminBean> getfilteredHistDiffTable() {
        return filteredHistDiffTable;
    }

    public void setfilteredHistDiffTable(List<AdminBean> filteredHistDiffTable) {
        this.filteredHistDiffTable = filteredHistDiffTable;
    }

    public void PieModel(int id) {

        System.out.println("GTWapp.AdminBean.PieModel()" + id);

        ArrayList num = DatabaseOperation.PieModel1RecordInDB(id);
        this.nomCompagneDiffusion = DatabaseOperation.GetNomCompagneInDB(id);

        System.out.println("Nom Compagne:/:/:" + DatabaseOperation.GetNomCompagneInDB(id));

        Rejected = (int) num.get(0);
        Delivred = (int) num.get(1);
        Waiting = (int) num.get(2);

    }

    private void createPieModels() {
        createPieModel1();
    }

    public void createPieModel1() {
        pieModel = new PieChartModel();

        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(Rejected);
        values.add(Delivred);
        values.add(Waiting);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Rejected");
        labels.add("Delivred");
        labels.add("Waiting");
        data.setLabels(labels);

        pieModel.setData(data);

    }

    public void APIDashbord(AdminBean newAdminObj) throws ParseException {
        System.out.println("GTWapp.AdminBean.APIDashbord()" + newAdminObj.datetimfin);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        DateFinLineChart = dateFormat.format(newAdminObj.datetimfin);
        DateDebutLineChart = dateFormat.format(newAdminObj.datetimedebut);

        Set set = DatabaseOperation.APIDashbordINDB(newAdminObj).entrySet();//Converting to Set so that we can traverse  
        Iterator itr = set.iterator();
        series1.setLabel("Series 1");

        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            System.out.println("" + entry.getKey() + "," + (Number) entry.getValue() + "");
            series1.set(entry.getKey(), (Number) entry.getValue());
            lastelement = entry.getKey();
        }

        Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(lastelement.toString());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DATE, 2);
        lastelement = dateFormat.format(cal.getTime());

    }

    public void createDateModel() {

        dateModel = new LineChartModel();
        dateModel.addSeries(series1);

        dateModel.setTitle("Date Debut:" + DateDebutLineChart + " ==> Date Fin:" + DateFinLineChart);
        dateModel.setZoom(true);

        dateModel.getAxis(AxisType.Y).setLabel("Values");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        axis.setMax(lastelement);

        axis.setTickFormat("%b/%m/%#d %H:%M:%S");
        dateModel.getAxes().put(AxisType.X, axis);

    }

    private void createDateModels() {
        createDateModel();
    }

    @PostConstruct
    public void init() {
        createPieModels();
        createDateModels();

        adminListFromDB = DatabaseOperation.getAdminListFromDB();
        userListFromDB = DatabaseOperation.getUserListFromDB();
        userProfilFromDB = DatabaseOperation.getUserProfilFromDB();
        userProfilNoFromDB = DatabaseOperation.getUserProfilNoFromDB();
        HistoriqueListFromDB = DatabaseOperation.showHist(idUtilisateur);
        adminFromDB = DatabaseOperation.showAdminByID(idUtilisateur);
        userFromDB = DatabaseOperation.showUserByID(idUtilisateur);
        HistoriqueEntetListFromDB = DatabaseOperation.showEntet(idUtilisateur);
        MessageListFromDB = DatabaseOperation.ListMessagesFromDb(idUtilisateur);
        GSMListFromDB = DatabaseOperation.ListGSMFromDb();
        DetailGSMListFromDB = DatabaseOperation.DetailsListGSMFromDb(idUtilisateur);
        NomListFromDB = DatabaseOperation.getNomListFromDB();
        NomMSGFromDB = DatabaseOperation.getNomMSGFromDB();
        DiffCompDisplayListFromDB = DatabaseOperation.DiffCompList(idUtilisateur);
        BlackListListFromDB = DatabaseOperation.BlackListList();

    }

}
