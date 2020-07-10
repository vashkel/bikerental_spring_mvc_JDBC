package com.example.bikerental.tag;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class CalculationTimeDialect extends AbstractProcessorDialect {

    public CalculationTimeDialect() {
        super("ctg Dialect", "ctg", 1000);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new CalculationTimeTag(dialectPrefix));
        return processors;
    }
}
