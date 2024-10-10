package ru.liga.parcelloader.type.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Используется для обозначения алгоритма, как порождающего грузовики, то есть
 * у алгоритма нет ограничения на максимальное количество грузовиков
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TrucksGenerator {
}
