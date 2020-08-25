package GTWapp;

import GTWappOperation.DatabaseOperation;
import controller.util.SessionUtils;
import dao.LoginDAO;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@ManagedBean
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private String login;
    private String pwd;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    //validate login
    public String ValidateUsernamePassword() {
        boolean valid = LoginDAO.validate(login, pwd);
        boolean validAdmin = LoginDAO.validateAdmin(login, pwd);
        boolean validUser = LoginDAO.validateUser(login, pwd);
    

        if (valid) {

            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", login);
           new DatabaseOperation().hist_action("Login");
            
            
            return "ListAdmin.xhtml?faces-redirect=true";
        } else if (validAdmin) {

            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", login);
new DatabaseOperation().hist_action("Login");
            return "ListUser.xhtml?faces-redirect=true";
        } else if (validUser) {

            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", login);
new DatabaseOperation().hist_action("Login");
            return "UserProfil.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vérifier votre username et mot de passe ", "Validation Error"));
            return "login";
        }
    }

    //logout event, invalidate session
    public String logout() {
        new DatabaseOperation().hist_action("Logout");
              FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(false);
            httpSession.invalidate();
            
            return "login.xhtml?faces-redirect=true";

        
       /* HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login.xhtml?faces-redirect=true";*/
    }
}
