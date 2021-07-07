//package com.ebsite.tempsite.controller.apis;
//
//import com.ebsite.tempsite.controller.ControllerBase;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//
//@RequestMapping("/users/")
//public class Users extends ControllerBase {
//
//    @Autowired
//    private UsersDao usersDao;
//    @RequestMapping("list")
//    @ResponseBody
//    public List<com.ebsite.tempsite.pojo.Users> Users(){
//        List<com.ebsite.tempsite.pojo.Users> lsUsers = usersDao.findAll();
//        return lsUsers;
//    }
//
//}
