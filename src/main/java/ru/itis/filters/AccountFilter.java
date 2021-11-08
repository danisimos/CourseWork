package ru.itis.filters;

import ru.itis.dao.UserRepository;
import ru.itis.dto.UserDto;
import ru.itis.models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/account/*")
public class AccountFilter implements Filter {
    public static final String USER_AUTHENTICATED_COOKIE_AND_ATTRIBUTE_NAME = "isAuthenticated";
    UserRepository userRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        userRepository = (UserRepository) servletContext.getAttribute("userRepository");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        HttpSession session = httpServletRequest.getSession(false);

        boolean isSession = session != null
                && session.getAttribute(USER_AUTHENTICATED_COOKIE_AND_ATTRIBUTE_NAME) != null
                && (Boolean) session.getAttribute(USER_AUTHENTICATED_COOKIE_AND_ATTRIBUTE_NAME);


        if(isSession) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else if(processCookies(httpServletRequest)) {
            User user = userRepository.findById(getUserIdFromCookie(httpServletRequest))
                    .orElseThrow(IllegalArgumentException::new);

            UserDto userDto = UserDto.fromUser(user);

            session = httpServletRequest.getSession(true);

            session.setAttribute(USER_AUTHENTICATED_COOKIE_AND_ATTRIBUTE_NAME, true);
            session.setAttribute("user", userDto);
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.sendRedirect("/signIn");
        }
    }

    private Integer getUserIdFromCookie(HttpServletRequest request) {
        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals("user"))
                return Integer.parseInt(cookie.getValue());
        }

        return -1;
    }

    private boolean processCookies(HttpServletRequest request) {
        if(request.getCookies() == null) return false;

        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals(USER_AUTHENTICATED_COOKIE_AND_ATTRIBUTE_NAME))
                return true;
        }

        return false;
    }

    @Override
    public void destroy() {

    }
}
