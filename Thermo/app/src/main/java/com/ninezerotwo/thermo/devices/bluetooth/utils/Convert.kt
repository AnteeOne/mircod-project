package com.ninezerotwo.thermo.devices.bluetooth.utils

class Convert {

    companion object{
        fun toBinary(value: Int): String{
            var number = value
            if(value < 0) number+=255
            return String.format("%8s",Integer.toBinaryString(number)).replace(" ","0")
        }
    }

}