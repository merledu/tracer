package tracer

import chisel3._


class TracerTop extends Module {
    val tracer = Module(new TraceUnit(M=false, C=false, F=false))
}
