package ru.itis.servlets;

import ru.itis.dto.SignInForm;
import ru.itis.dto.UserDto;
import ru.itis.filters.AccountFilter;
import ru.itis.services.SignInService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {
    SignInService signInService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        signInService = (SignInService) servletContext.getAttribute("signInService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("signIn.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SignInForm signInForm = SignInForm.builder()
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();

        UserDto userDto = signInService.signIn(signInForm);

        if(userDto == null) response.sendRedirect("/signIn");
        else {
            Cookie authenticatedCookie = new Cookie(AccountFilter.USER_AUTHENTICATED_COOKIE_AND_ATTRIBUTE_NAME, "true");
            Cookie userIdCookie = new Cookie("user", userDto.getId().toString());

            response.addCookie(authenticatedCookie);
            response.addCookie(userIdCookie);

            HttpSession session = request.getSession(true);
            session.setAttribute(AccountFilter.USER_AUTHENTICATED_COOKIE_AND_ATTRIBUTE_NAME, true);
            session.setAttribute("user", userDto);

            response.sendRedirect("/account");
        }
    }
}
