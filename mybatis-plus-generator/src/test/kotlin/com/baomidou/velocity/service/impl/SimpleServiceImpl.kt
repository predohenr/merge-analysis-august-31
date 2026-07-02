package com.baomidou.velocity.service.impl;

import com.baomidou.velocity.entity.Simple;
import com.baomidou.velocity.mapper.SimpleMapper;
import com.baomidou.velocity.service.ISimpleService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-27
 */
@Service
open class SimpleServiceImpl : ServiceImpl<SimpleMapper, Simple>(), ISimpleService {

}
