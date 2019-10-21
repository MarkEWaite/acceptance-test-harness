package org.jenkinsci.test.acceptance.plugins.logparser;

import hudson.util.VersionNumber;
import org.jenkinsci.test.acceptance.po.Control;
import org.jenkinsci.test.acceptance.po.JenkinsConfig;
import org.jenkinsci.test.acceptance.po.PageAreaImpl;
import org.jenkinsci.test.acceptance.po.PageObject;

/**
 * Helper class for configuring global settings of LogParser.
 *
 * @author Daniel Sikeler.
 */
public class LogParserGlobalConfig extends PageAreaImpl {

    /**
     * Repeatable-add Button.
     */
    private final Control addButton = control("repeatable-add");
    private final String rulePrefix;

    public static LogParserGlobalConfig getInstance(JenkinsConfig context) {
        boolean old = context.jenkins.getPlugin("log-parser").getVersion().isOlderThan(new VersionNumber("2.1"));
        String path = old
                ? "/hudson-plugins-logparser-LogParserPublisher/log-parser"
                : "/hudson-plugins-logparser-LogParserPublisher"
        ;
        String rulePrefix = old ? "rule" : "parsingRulesGlobal";
        return new LogParserGlobalConfig(context, path, rulePrefix);
    }

    private LogParserGlobalConfig(JenkinsConfig context, String path, String rulePrefix) {
        super(context, path);
        this.rulePrefix = rulePrefix;
    }

    /**
     * Add a new LogParser configuration.
     * @param description The description.
     * @param path The path to the rules file.
     */
    public void addParserConfig(String description, String path) {
        String rulePath = createPageArea(rulePrefix, addButton::click);
        Rule rule = new Rule(getPage(), rulePath);
        rule.description.set(description);
        rule.path.set(path);
    }

    /**
     * PageArea generated after repeatable-add is clicked.
     */
    private static class Rule extends PageAreaImpl {
        private Rule(PageObject parent, String path) {
            super(parent, path);
        }

        /**
         * Description input-field.
         */
        public final Control description = control("name");
        /**
         * Path input-field.
         */
        public final Control path = control("path");
    }
}
