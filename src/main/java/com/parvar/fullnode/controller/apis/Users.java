package com.parvar.fullnode.controller.apis;

import com.parvar.fullnode.controller.ControllerBase;
import com.parvar.fullnode.dao.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("/users/")
public class Users extends ControllerBase {

    @Autowired
    private UsersDao usersDao;
    @RequestMapping("list")
    @ResponseBody
    public List<com.parvar.fullnode.pojo.Users> Users(){
        List<com.parvar.fullnode.pojo.Users> lsUsers = usersDao.findAll();
        return lsUsers;
    }

}
