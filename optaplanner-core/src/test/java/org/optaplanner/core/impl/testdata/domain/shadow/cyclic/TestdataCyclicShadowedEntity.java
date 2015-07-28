/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.core.impl.testdata.domain.shadow.cyclic;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.CustomShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.impl.domain.entity.descriptor.EntityDescriptor;
import org.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import org.optaplanner.core.impl.domain.variable.descriptor.GenuineVariableDescriptor;
import org.optaplanner.core.impl.testdata.domain.TestdataObject;
import org.optaplanner.core.impl.testdata.domain.TestdataValue;

@PlanningEntity
public class TestdataCyclicShadowedEntity extends TestdataObject {

    public static EntityDescriptor buildEntityDescriptor() {
        SolutionDescriptor solutionDescriptor = TestdataCyclicShadowedSolution.buildSolutionDescriptor();
        return solutionDescriptor.findEntityDescriptorOrFail(TestdataCyclicShadowedEntity.class);
    }

    public static GenuineVariableDescriptor buildVariableDescriptorForValue() {
        SolutionDescriptor solutionDescriptor = TestdataCyclicShadowedSolution.buildSolutionDescriptor();
        EntityDescriptor entityDescriptor = solutionDescriptor.findEntityDescriptorOrFail(TestdataCyclicShadowedEntity.class);
        return entityDescriptor.getGenuineVariableDescriptor("value");
    }

    private TestdataValue value;
    private String rockShadow;
    private String paperShadow;
    private String scissorsShadow;

    public TestdataCyclicShadowedEntity() {
    }

    public TestdataCyclicShadowedEntity(String code) {
        super(code);
    }

    public TestdataCyclicShadowedEntity(String code, TestdataValue value) {
        this(code);
        this.value = value;
    }

    @PlanningVariable(valueRangeProviderRefs = "valueRange")
    public TestdataValue getValue() {
        return value;
    }

    public void setValue(TestdataValue value) {
        this.value = value;
    }

    @CustomShadowVariable(variableListenerClass = RockShadowUpdatingVariableListener.class,
            sources = {@CustomShadowVariable.Source(variableName = "scissorsShadow")})
    public String getRockShadow() {
        return rockShadow;
    }

    public void setRockShadow(String rockShadow) {
        this.rockShadow = rockShadow;
    }

    @CustomShadowVariable(variableListenerClass = PaperShadowUpdatingVariableListener.class,
            sources = {@CustomShadowVariable.Source(variableName = "rockShadow")})
    public String getPaperShadow() {
        return paperShadow;
    }

    public void setPaperShadow(String paperShadow) {
        this.paperShadow = paperShadow;
    }

    @CustomShadowVariable(variableListenerClass = ScissorsShadowUpdatingVariableListener.class,
            sources = {@CustomShadowVariable.Source(variableName = "paperShadow")})
    public String getScissorsShadow() {
        return scissorsShadow;
    }

    public void setScissorsShadow(String scissorsShadow) {
        this.scissorsShadow = scissorsShadow;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

}
