package ru.itis.servlets;

import ru.itis.dao.CurriculumVitaeRepository;
import ru.itis.dto.UserDto;
import ru.itis.models.CurriculumVitae;
import ru.itis.models.Vacancy;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/account/myCV")
public class MyCurriculumVitaeServlet extends HttpServlet {
    CurriculumVitaeRepository curriculumVitaeRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        curriculumVitaeRepository = (CurriculumVitaeRepository) servletContext.getAttribute("curriculumVitaeRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDto userDto = (UserDto) request.getSession().getAttribute("user");
        List<CurriculumVitae> curriculumVitaeList = curriculumVitaeRepository.findByAuthorId(userDto.getId());

        request.setAttribute("curriculumVitaeList", curriculumVitaeList);
        request.getRequestDispatcher("myCV.ftl").forward(request, response);
    }
}
