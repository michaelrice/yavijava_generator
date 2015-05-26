package com.toastcoders.vmware.yavijava.parsers

import com.toastcoders.vmware.yavijava.contracts.WSDLParser
import com.toastcoders.vmware.yavijava.data.DataObject

/**
 *  Copyright 2015 Michael Rice <michael@michaelrice.org>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
class EnumWSDLParserImpl implements WSDLParser {

    DataObject dataObject

    @Override
    void parse(String wsdl) {
        XmlSlurper slurper = new XmlSlurper()
        def doc = slurper.parseText(wsdl).declareNamespace(['vim25': 'xmlns:vim25="urn:vim25"'])
        String className = doc."@name"
        dataObject = new DataObject()
        dataObject.name = className
        def props = doc."restriction"."enumeration"
        props.each {
            dataObject.objProperties << (it."@value" as String)
        }
        assert props.size() == dataObject.objProperties.size()
    }
}