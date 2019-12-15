package com.pms.projectmanagement.controllers;

import com.pms.projectmanagement.dtos.ProjectDto;
import com.pms.projectmanagement.dtos.UserDto;
import com.pms.projectmanagement.notifications.Notification;
import com.pms.projectmanagement.notifications.notificationService.NotificationService;
import com.pms.projectmanagement.services.invite.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"projectInfo","user"})
public class InvitationController {
    private final InvitationService inviteService;
    private final NotificationService notificationService;
    @RequestMapping(value = "/inviteDeveloper",method = RequestMethod.POST)
    public String inviteDeveloper(@SessionAttribute("projectInfo") ProjectDto projectDto,
                                  @SessionAttribute("user") UserDto userDto,
                                  Model model,
                                  @RequestParam(value = "userId",required = false) String userId){

        inviteService.inviteDeveloper(userId,projectDto.getId().toString());
        notificationService.sendInviteNotification(userId,userDto.getId().toString(),projectDto);
        return "redirect:/dashboard";
    }

}
