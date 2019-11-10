package application.service.impl;

import org.springframework.stereotype.Service;

import application.entity.Contact;
import application.entity.ContactExample;
import application.mapper.ContactMapper;
import application.service.ContactService;

@Service
public class ContactServiceImpl extends BaseServiceImpl<ContactMapper, Contact, ContactExample> implements ContactService {

}
