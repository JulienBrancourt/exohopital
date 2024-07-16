package org.example.exohopital.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.exohopital.models.Patient;
import org.example.exohopital.service.PatientService;
import org.example.exohopital.util.HibernateSession;
import org.hibernate.internal.util.collections.SingletonIterator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "patientservlet", value = "/patient/*")
@MultipartConfig
public class PatientServlet extends HttpServlet {
    private PatientService patientService;

    @Override
    public void init() throws ServletException {
        patientService = new PatientService(HibernateSession.getSessionFactory());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo().substring(1);

        HttpSession sessionlog = (HttpSession) req.getSession(false);

        boolean logged = (sessionlog != null && sessionlog.getAttribute("isLogged") != null) ? (boolean) sessionlog.getAttribute("isLogged") : false;

        String loggedString;
        if (!logged) {
            loggedString = "false";
        } else {
            loggedString = "test";
        }

        req.setAttribute("isLogged", loggedString);

        switch (action) {
            case "global":
                showAll(req, resp);
                break;
            case "add":
                showAll(req, resp);
                break;
            case "detail":
                showDetails(req, resp);
                break;
            case "login":
                showLoginForm(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo().substring(1);

        switch (action) {
            case "add":
                createPatient(req, resp);
                break;
            case "login":
                loginUser(req, resp);
                break;
            default:
                doGet(req, resp);
                break;
        }
    }

    private boolean isUserLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute("isLogged") != null && (Boolean) session.getAttribute("isLogged");
    }

    protected void showAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Object> patients = patientService.getPatients();
        req.setAttribute("patients", patientService.getPatients());
        req.getRequestDispatcher("/WEB-INF/patients.jsp").forward(req, resp);
    }

    protected void showDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Patient patient = patientService.getPatient(id);
        req.setAttribute("patient", patient);
        req.getRequestDispatcher("/WEB-INF/patientdetails.jsp").forward(req, resp);
    }

    private void createPatient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lastname = req.getParameter("lastname");
        String firstname = req.getParameter("firstname");
        LocalDate birthDate = LocalDate.parse(req.getParameter("birthDate"));

        String uploadPath = getServletContext().getRealPath("/")+"image";
        File file = new File(uploadPath);
        if (!file.exists()){
            file.mkdir();
        }
        Part image = req.getPart("imagePath");
        String fileName = image.getSubmittedFileName();
        image.write(uploadPath+File.separator+fileName);
        String imagePath = uploadPath+File.separator+fileName;
//todo: trouver chemin pour l'image (pas d'upload en bdd)
        boolean isCreated = patientService.createPatient(lastname, firstname, birthDate, imagePath );

        if (isCreated) {
            resp.sendRedirect(req.getContextPath() + "/patient/global");
        } else {
            req.setAttribute("error", "échec !");
            req.getRequestDispatcher("/WEB-INF/patients.jsp").forward(req, resp);
        }
    }

    private void showLoginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    private void loginUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("admin".equals(username) && "admin".equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("isLogged", true);
            resp.sendRedirect(req.getContextPath() + "/patient/global");
        } else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
    }
}
