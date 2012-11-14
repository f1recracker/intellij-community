/*
 * Copyright 2000-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.psi.codeStyle.arrangement

import com.intellij.psi.codeStyle.arrangement.match.ArrangementModifier
import org.junit.Before

import static com.intellij.psi.codeStyle.arrangement.match.ArrangementModifier.PROTECTED
import static com.intellij.psi.codeStyle.arrangement.match.ArrangementModifier.PUBLIC

/**
 * @author Denis Zhdanov
 * @since 11/14/12 1:29 PM
 */
class JavaRearrangerByNameTest extends AbstractJavaRearrangerTest {

  @Before
  void setUp() {
    super.setUp()
    commonSettings.BLANK_LINES_AROUND_METHOD = 0
    commonSettings.BLANK_LINES_AROUND_CLASS = 0
  }
  
  void "test only name condition"() {
    doTest(
      initial: '''\
class Test {
  public void setI() {}
  public void getI() {}
  public void test() {}
}''',
      expected: '''\
class Test {
  public void getI() {}
  public void setI() {}
  public void test() {}
}''',
      rules: [rule("get.*")]
    )
  }
  
  void "test name condition and others"() {
    doTest(
      initial: '''\
class Test {
  private void getInner() {}
  public void getOuter() {}
  protected void test() {}
}''',
      expected: '''\
class Test {
  public void getOuter() {}
  protected void test() {}
  private void getInner() {}
}''',
      rules: [rule("get.*", PUBLIC), rule(PROTECTED)]
    )
  }
}
