package ru.itis.servlets;

import ru.itis.dao.CurriculumVitaeRepository;
import ru.itis.dao.UserRepository;
import ru.itis.dao.VacancyRepository;
import ru.itis.dto.UserDto;
import ru.itis.models.CurriculumVitae;
import ru.itis.models.Vacancy;
import ru.itis.services.FilesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    VacancyRepository vacancyRepository;
    CurriculumVitaeRepository curriculumVitaeRepository;
    UserRepository userRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        vacancyRepository = (VacancyRepository) servletContext.getAttribute("vacancyRepository");
        curriculumVitaeRepository = (CurriculumVitaeRepository) servletContext.getAttribute("curriculumVitaeRepository");
        userRepository = (UserRepository) servletContext.getAttribute("userRepository");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = Integer.parseInt(request.getParameter("userId"));

        List<Vacancy> vacancyList = vacancyRepository.findByAuthorId(userId);
        List<CurriculumVitae> curriculumVitaeList = curriculumVitaeRepository.findByAuthorId(userId);
        UserDto userDto = UserDto.fromUser(userRepository.findById(userId).orElseThrow(IllegalArgumentException::new));

        request.setAttribute("vacancyList", vacancyList);
        request.setAttribute("curriculumVitaeList", curriculumVitaeList);
        request.setAttribute("userOnPage", userDto);

        request.getRequestDispatcher("user.ftl").forward(request, response);
    }
}
