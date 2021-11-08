package ru.itis.servlets;

import ru.itis.models.FileInfo;
import ru.itis.services.FilesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/files/*")
public class FilesDownloadServlet extends HttpServlet {
    FilesService filesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        filesService = (FilesService) servletContext.getAttribute("filesService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer fileId;

        String fileIdString = request.getRequestURI().substring(7);
        fileId = Integer.parseInt(fileIdString);

        FileInfo fileInfo = filesService.getFileInfoById(fileId);
        response.setContentType(fileInfo.getType());
        response.setContentLength(fileInfo.getSize().intValue());
        response.setHeader("Content-Disposition", "fileName=\"" + fileInfo.getOriginalFileName() + "\"");
        filesService.readFileFromStorage(fileId, response.getOutputStream());
    }
}
