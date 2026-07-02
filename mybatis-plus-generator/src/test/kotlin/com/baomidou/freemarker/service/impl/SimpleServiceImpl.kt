package com.baomidou.freemarker.service.impl;

import com.baomidou.freemarker.entity.Simple;
import com.baomidou.freemarker.mapper.SimpleMapper;
import com.baomidou.freemarker.service.ISimpleService;
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
