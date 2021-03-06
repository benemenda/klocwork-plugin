package com.emenda.klocwork.pipeline;

import com.emenda.klocwork.KlocworkConstants;
import com.emenda.klocwork.KlocworkServerLoadBuilder;
import com.emenda.klocwork.config.KlocworkServerLoadConfig;
import com.google.inject.Inject;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousNonBlockingStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;


public class KlocworkServerLoadStep extends AbstractStepImpl {

    private KlocworkServerLoadConfig serverConfig;

    @DataBoundConstructor
    public KlocworkServerLoadStep(KlocworkServerLoadConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    // @DataBoundSetter
    // public void setServerConfig(KlocworkServerLoadConfig serverConfig) {
    //     this.serverConfig = serverConfig;
    // }

    public KlocworkServerLoadConfig getServerConfig() { return serverConfig; }


    private static class KlocworkServerLoadStepExecution extends AbstractSynchronousNonBlockingStepExecution<Void> {

        private static final long serialVersionUID = 1L;

        @Inject
        private transient KlocworkServerLoadStep step;

        @StepContextParameter
        @SuppressWarnings("unused")
        private transient Run build;

        @StepContextParameter
        @SuppressWarnings("unused")
        private transient FilePath workspace;

        @StepContextParameter
        @SuppressWarnings("unused")
        private transient Launcher launcher;

        @StepContextParameter
        @SuppressWarnings("unused")
        private transient TaskListener listener;

        @StepContextParameter
        private transient EnvVars env;

        @Override
        protected Void run() throws Exception {

            KlocworkServerLoadBuilder builder = new KlocworkServerLoadBuilder(step.getServerConfig());
            builder.perform(build, env, workspace, launcher, listener);
            return null;
        }
    }

    @Extension(optional = true)
    public static class DescriptorImpl extends AbstractStepDescriptorImpl {
        public DescriptorImpl() {
            super(KlocworkServerLoadStepExecution.class);
        }

        @Override
        public String getFunctionName() {
            return "klocworkIntegrationStep2";
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return KlocworkConstants.KLOCWORK_SERVER_LOAD_DISPLAY_NAME;
        }
    }
}
