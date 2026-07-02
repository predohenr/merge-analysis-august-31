package com.baomidou.mybatisplus.test.h2.kotlin.service.impl

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl
import com.baomidou.mybatisplus.test.h2.kotlin.entity.KtH2User
import com.baomidou.mybatisplus.test.h2.kotlin.mapper.KtUserMapper
import com.baomidou.mybatisplus.test.h2.kotlin.service.KtH2UserService
import org.springframework.stereotype.Service

@Service
class KtH2UserServiceImpl : ServiceImpl<KtUserMapper, KtH2User>(), KtH2UserService {

}
