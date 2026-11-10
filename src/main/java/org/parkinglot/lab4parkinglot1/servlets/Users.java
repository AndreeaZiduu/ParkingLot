package org.parkinglot.lab4parkinglot1.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parkinglot.lab4parkinglot1.common.CarDto;
import org.parkinglot.lab4parkinglot1.common.UserDto;
import org.parkinglot.lab4parkinglot1.ejb.CarsBean;
import org.parkinglot.lab4parkinglot1.ejb.UsersBean;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Users", value = "/Users")
    public class Users extends HttpServlet {

    @Inject
    private UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }
}

