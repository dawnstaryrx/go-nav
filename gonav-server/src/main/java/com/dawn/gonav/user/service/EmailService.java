package com.dawn.gonav.user.service;

import com.dawn.gonav.model.dto.EmailDTO;

public interface EmailService {
    void sendMsg(EmailDTO emailDTO);
}
