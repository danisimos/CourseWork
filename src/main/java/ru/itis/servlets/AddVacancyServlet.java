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

@WebServlet("/account/addMyVacancy")
public class AddVacancyServlet extends HttpServlet {
    VacancyRepository vacancyRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        vacancyRepository = (VacancyRepository) servletContext.getAttribute("vacancyRepository");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String content = request.getParameter("content");

        UserDto userDto = (UserDto) request.getSession().getAttribute("user");

        Vacancy vacancy = Vacancy.builder()
                .content(content)
                .author(UserDto.toUser(userDto))
                .build();

        vacancyRepository.save(vacancy);
    }
}
