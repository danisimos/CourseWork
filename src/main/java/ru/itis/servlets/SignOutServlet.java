package ru.itis.servlets;

import ru.itis.filters.AccountFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/signOut")
public class SignOutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        session.removeAttribute("user");
        session.removeAttribute(AccountFilter.USER_AUTHENTICATED_COOKIE_AND_ATTRIBUTE_NAME);

        Cookie authenticatedCookie = new Cookie(AccountFilter.USER_AUTHENTICATED_COOKIE_AND_ATTRIBUTE_NAME, "false");
        authenticatedCookie.setMaxAge(0);
        Cookie userIdCookie = new Cookie("user", null);
        userIdCookie.setMaxAge(0);

        response.addCookie(authenticatedCookie);
        response.addCookie(userIdCookie);

        response.sendRedirect("/");
    }
}
