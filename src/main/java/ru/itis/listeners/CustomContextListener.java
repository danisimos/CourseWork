package ru.itis.listeners;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.dao.CurriculumVitaeRepository;
import ru.itis.dao.FilesRepository;
import ru.itis.dao.UserRepository;
import ru.itis.dao.VacancyRepository;
import ru.itis.dao.impl.CurriculumVitaeRepositoryImpl;
import ru.itis.dao.impl.FilesRepositoryImpl;
import ru.itis.dao.impl.UserRepositoryImpl;
import ru.itis.dao.impl.VacancyRepositoryImpl;
import ru.itis.services.FilesService;
import ru.itis.services.PasswordEncoderService;
import ru.itis.services.SignInService;
import ru.itis.services.SignUpService;
import ru.itis.services.impl.FilesServiceImpl;
import ru.itis.services.impl.PasswordEncoderServiceImpl;
import ru.itis.services.impl.SignInServiceImpl;
import ru.itis.services.impl.SignUpServiceImpl;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CustomContextListener implements ServletContextListener {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123456";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/itisprogramming";
    private static final String DB_DRIVER = "org.postgresql.Driver";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        UserRepository userRepository = new UserRepositoryImpl(dataSource);
        VacancyRepository vacancyRepository = new VacancyRepositoryImpl(dataSource);
        FilesRepository filesRepository = new FilesRepositoryImpl(dataSource);
        PasswordEncoderService passwordEncoderService = new PasswordEncoderServiceImpl();
        SignUpService signUpService = new SignUpServiceImpl(userRepository, passwordEncoderService);
        SignInService signInService = new SignInServiceImpl(userRepository, passwordEncoderService);
        FilesService filesService = new FilesServiceImpl(filesRepository, userRepository);
        CurriculumVitaeRepository curriculumVitaeRepository = new CurriculumVitaeRepositoryImpl(dataSource);

        servletContext.setAttribute("userRepository", userRepository);
        servletContext.setAttribute("vacancyRepository", vacancyRepository);
        servletContext.setAttribute("curriculumVitaeRepository", curriculumVitaeRepository);
        servletContext.setAttribute("signUpService", signUpService);
        servletContext.setAttribute("signInService", signInService);
        servletContext.setAttribute("filesService", filesService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
