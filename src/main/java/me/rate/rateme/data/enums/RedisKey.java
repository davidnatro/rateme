package me.rate.rateme.data.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RedisKey {

    TASK("tasks:", 2L);

    public final String key;
    public final Long ttlInHours;
}
