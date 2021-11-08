package ru.itis.servlets;

import ru.itis.dao.CurriculumVitaeRepository;
import ru.itis.dto.UserDto;
import ru.itis.models.CurriculumVitae;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/account/addMyCV")
public class AddCurriculumVitaeServlet extends HttpServlet {
    CurriculumVitaeRepository curriculumVitaeRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        curriculumVitaeRepository = (CurriculumVitaeRepository) servletContext.getAttribute("curriculumVitaeRepository");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String content = request.getParameter("content");

        UserDto userDto = (UserDto) request.getSession().getAttribute("user");

        CurriculumVitae curriculumVitae = CurriculumVitae.builder()
                .content(content)
                .author(UserDto.toUser(userDto))
                .build();

        curriculumVitaeRepository.save(curriculumVitae);
    }
}
