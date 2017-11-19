package net.itw.wcms.x27.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.x27.entity.Role;
import net.itw.wcms.x27.repository.PrivilegeRepository;
import net.itw.wcms.x27.service.IPrivilegeService;

@Service
@Transactional
public class PrivilegeServiceImpl implements IPrivilegeService {

	@Autowired
	private PrivilegeRepository privilegeRepository;
}
