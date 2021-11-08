package ru.itis.servlets;

import ru.itis.dao.VacancyRepository;
import ru.itis.dto.UserDto;
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

@WebServlet("/account/myVacancy")
public class MyVacancyServlet extends HttpServlet {
    VacancyRepository vacancyRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        vacancyRepository = (VacancyRepository) servletContext.getAttribute("vacancyRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDto userDto = (UserDto) request.getSession().getAttribute("user");
        List<Vacancy> vacancyList = vacancyRepository.findByAuthorId(userDto.getId());

        request.setAttribute("vacancyList", vacancyList);
        request.getRequestDispatcher("myVacancy.ftl").forward(request, response);
    }
}
