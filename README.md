# Testing with JUnit
Source code repository of the book [Testing with JUnit](http://www.codeaffine.com/testing-with-junit/)

[![Build Status](https://travis-ci.org/fappel/Testing-with-JUnit.png)](https://travis-ci.org/fappel/Testing-with-JUnit)
[![Coverage Status](https://coveralls.io/repos/fappel/Testing-with-JUnit/badge.svg)](https://coveralls.io/r/fappel/Testing-with-JUnit)
[![Book Details](https://img.shields.io/badge/Testing%20with-JUnit-green.svg)](http://www.codeaffine.com/testing-with-junit/)


<table width="100%">
<tr><td width="250px">
<a href="http://www.codeaffine.com/testing-with-junit/" target="_blank"><img src="/images/testing-with-junit-book-cover.png" width="230px" height="270px"></a>
</td>
<td>
<p>Testing with JUnit gives a profound entry point in the essentials of unit testing and prepares you for test-related daily work challenges. For the best possible understanding, I have hosted the complete source code of the book's example app at this repository.</p>
<p>This allows you to comprehend the various aspects of JUnit testing from within a more complex development context and facilitates an exchange of ideas using the repository's issue tracker.</p>
</td></tr>
<table>

<table width="100%">
<tr><td>
<p>You will find a separate folder for each chapter, providing a complete project configuration with all dependencies and sources. This means navigating to this directory and using the 'mvn test' Maven command should enable you to compile
and run the given examples easily. An exception from the rule is chapter eight, where the pom.xml is located in the subfolder 'build'.</p>
</td>
<td width="380px">
<a href="http://www.codeaffine.com/testing-with-junit/" target="_blank"><img src="/images/B03130_08_05.png" width="360px" height="200px"></a>
</td></tr>
<table>

<table width="100%">
<tr><td width="270px">
<a href="http://www.codeaffine.com/testing-with-junit/" target="_blank"><img src="/images/B03130_01_07.png" width="250px" height="250px"></a>
</td>
<td>
<p>Chapter eight contains the complete sample application and can be launched on various platforms and widget toolkits. Look out for the 'Application' classes in the 'timeline.swing.application' or 'timeline.swt.application' projects, or use the <a href="http://www.eclipse.org/rap/" target="_blank">RAP</a> 'Configuration' in the 'timeline.tabris' project, if you want to run the web or mobile modules. In case you are using <a href="http://www.eclipse.org/downloads/packages/eclipse-rcp-and-rap-developers/marsr" target="_blank">Eclipse</a> the projects contain predefined lauch configurations.</p>
<p>Note that the sample is not meant to be production ready, using splash screens or the like on startup. In fact, the first launch will take some time as it clones the <a href="https://github.com/junit-team/junit" target="_blank">JUnit Git Repository</a> using its commits as timeline content.</p>
</td></tr>
<table>

License
---
The source code is published under the [Eclipse Public License - v 1.0](https://www.eclipse.org/legal/epl-v10.html)
