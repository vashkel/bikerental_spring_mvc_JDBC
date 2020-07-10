package com.example.bikerental.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.thymeleaf.standard.processor.StandardWithTagProcessor.PRECEDENCE;

public class CalculationTimeTag extends AbstractAttributeTagProcessor {
    private static Logger logger = LogManager.getLogger(CalculationTimeTag.class);

    private static final long serialVersionUID = -5306548341656156312L;

    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private static final String ATTR_NAME = "calculateTime";

    protected CalculationTimeTag(final String dialectPrefix){
        super(TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix,     // Prefix to be applied to name for matching
                null,              // No tag name: match any tag name
                false,             // No prefix to be applied to tag name
                ATTR_NAME,         // Name of the attribute that will be matched
                true,              // Apply dialect prefix to attribute name
                PRECEDENCE,        // Precedence (inside dialect's precedence)
                true);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }
    
//    public int doStartTag() {
//
//        try {
//            JspWriter out = pageContext.getOut();
//
//            if (startTime != null && finishTime != null) {
//                LocalDateTime dateFrom = startTime;
//                LocalDateTime dateTo = finishTime;
//
//                long difference = ChronoUnit.MINUTES.between(dateFrom, dateTo);
//
//                out.write(String.valueOf(difference));
//            }
//        } catch (IOException e) {
//            logger.log(Level.ERROR, "Write tag error, " + e);
//        }
//
//        return SKIP_BODY;
//    }


    @Override
    protected void doProcess(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {
        if (startTime != null && finishTime != null) {
            LocalDateTime dateFrom = startTime;
            LocalDateTime dateTo = finishTime;

            long difference = ChronoUnit.MINUTES.between(dateFrom, dateTo);

            structureHandler.setBody(String.valueOf(difference),false);
        }
    }
}
