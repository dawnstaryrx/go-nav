package com.dawn.gonav.common.service;

import com.dawn.gonav.model.dto.EmailDTO;

public interface EmailService {
    void sendMsg(EmailDTO emailDTO);
}
