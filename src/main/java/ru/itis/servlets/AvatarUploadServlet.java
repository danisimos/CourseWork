package ru.itis.servlets;

import ru.itis.dao.UserRepository;
import ru.itis.dto.UserDto;
import ru.itis.filters.AccountFilter;
import ru.itis.models.User;
import ru.itis.services.FilesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/account/updateAvatar")
@MultipartConfig
public class AvatarUploadServlet extends HttpServlet {
    FilesService filesService;
    UserRepository userRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        filesService = (FilesService) servletContext.getAttribute("filesService");
        userRepository = (UserRepository) servletContext.getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("avatarUpload.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("file");
        UserDto userDto = (UserDto) request.getSession().getAttribute("user");

        filesService.saveFileToStorage(userDto
                ,part.getInputStream()
                ,part.getSubmittedFileName()
                ,part.getContentType()
                ,part.getSize());

        User user = userRepository.findById(userDto.getId())
                .orElseThrow(IllegalArgumentException::new);

        userDto = UserDto.fromUser(user);

        HttpSession session = request.getSession(false);

        session.setAttribute(AccountFilter.USER_AUTHENTICATED_COOKIE_AND_ATTRIBUTE_NAME, true);
        session.setAttribute("user", userDto);

        response.sendRedirect("/account");
    }
}
