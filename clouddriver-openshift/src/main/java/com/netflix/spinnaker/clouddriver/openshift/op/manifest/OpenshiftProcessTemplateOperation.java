/*
 * Copyright 2020 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.netflix.spinnaker.clouddriver.openshift.op.manifest;

import com.netflix.spinnaker.clouddriver.deploy.DeployDescription;
import com.netflix.spinnaker.clouddriver.jobs.JobRequest;
import com.netflix.spinnaker.clouddriver.jobs.JobResult;
import com.netflix.spinnaker.clouddriver.jobs.local.JobExecutorLocal;
import com.netflix.spinnaker.clouddriver.kubernetes.v2.op.OperationResult;
import com.netflix.spinnaker.clouddriver.orchestration.AtomicOperation;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenshiftProcessTemplateOperation implements AtomicOperation<OperationResult> {
  private final DeployDescription description;

  public OpenshiftProcessTemplateOperation(DeployDescription description) {
    this.description = description;
  }

  @Override
  public OperationResult operate(List priorOutputs) {
    JobRequest jobRequest =
        new JobRequest(Arrays.asList("oc process -f Manifest.yml -o yaml".split(" ")));
    JobResult<String> jobResult = new JobExecutorLocal(1).runJob(jobRequest);
    log.warn(jobResult.getError());
    log.warn(jobResult.getOutput());
    log.warn(jobResult.getResult().toString());
    return null;
  }
}
