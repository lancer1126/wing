package fun.lance.common.cache.utils;

import fun.lance.common.constants.CacheConst;
import fun.lance.common.constants.enums.BusinessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class BusinessSnGenerator {

    private final RedisTemplate<Object, Object> redisTemplate;

    public String generateSerialNo(BusinessType businessType) {
        // 默认6位
        return generateSerialNo(businessType, 6);
    }

    public String generateSerialNo(BusinessType businessType, Integer digit) {
        if (businessType == null) {
            businessType = BusinessType.COMMON;
        }
        String nowDate = LocalDateTime
                .now(ZoneOffset.of("+8"))
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String key = CacheConst.SN_PREFIX + businessType.value() + ":" + nowDate;
        Long increment = redisTemplate.opsForValue().increment(key);
        return nowDate + String.format("%0" + digit + "d", increment);
    }
}
