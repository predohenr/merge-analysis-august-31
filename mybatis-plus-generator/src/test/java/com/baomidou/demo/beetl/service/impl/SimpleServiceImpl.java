package com.baomidou.demo.beetl.service.impl;

import com.baomidou.demo.beetl.entity.Simple;
import com.baomidou.demo.beetl.mapper.SimpleMapper;
import com.baomidou.demo.beetl.service.ISimpleService;
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
public class SimpleServiceImpl extends ServiceImpl<SimpleMapper, Simple> implements ISimpleService {

}
