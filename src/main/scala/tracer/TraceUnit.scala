package tracer

import chisel3._
import configs.Configs


class TracerIO extends Bundle with Configs {
    // Inputs
    val rvfiUIntVec    = Input(Vec(4, UInt(XLEN.W)))
    val rvfiRegAddrVec = Input(Vec(3, UInt(REG_ADDR_WIDTH.W)))
    val rvfiSIntVec    = Input(Vec(5, SInt(XLEN.W)))
    val rvfiBoolVec    = Input(Vec(1, Bool()))
    val rvfi_mode      = Input(UInt(2.W))
}


class TraceUnit(M: Boolean, C: Boolean, F: Boolean) extends Module with Configs {
    val io: TracerIO = IO(new TracerIO)

    val clkCycle = RegInit(0.U(XLEN.W))
    clkCycle := clkCycle + 1.U

    val rvfiUIntWires: Map[String, UInt] = Map(
        "insn"     -> io.rvfiUIntVec(0),
        "pc_rdata" -> io.rvfiUIntVec(1),
        "pc_wdata" -> io.rvfiUIntVec(2),
        "mem_addr" -> io.rvfiUIntVec(3),
        "rd_addr"  -> io.rvfiRegAddrVec(0),
        "rs1_addr" -> io.rvfiRegAddrVec(1),
        "rs2_addr" -> io.rvfiRegAddrVec(2),
        "mode"     -> io.rvfi_mode
    )
    val rvfiSIntWires: Map[String, SInt] = Map(
        "rd_wdata"  -> io.rvfiSIntVec(0),
        "rs1_rdata" -> io.rvfiSIntVec(1),
        "rs2_rdata" -> io.rvfiSIntVec(2),
        "mem_rdata" -> io.rvfiSIntVec(3),
        "mem_wdata" -> io.rvfiSIntVec(4)
    )
    val rvfiBoolWires: Map[String, Bool] = Map(
        "valid" -> io.rvfiBoolVec(0)
    )

    when (rvfiBoolWires("valid")) {
        printf(
            "ClkCycle: %d, pc_rdata: %x, pc_wdata: %x, insn: %x, mode: %d, rs1_addr: %d, rs1_rdata: %x, rs2_addr: %d, rs2_rdata: %x, rd_addr: %d, rd_wdata: %x, mem_addr: %x, mem_rdata: %x, mem_wdata: %x\n",
            clkCycle, rvfiUIntWires("pc_rdata"), rvfiUIntWires("pc_wdata"), rvfiUIntWires("insn"), rvfiUIntWires("mode"),
            rvfiUIntWires("rs1_addr"), rvfiSIntWires("rs1_rdata"), rvfiUIntWires("rs2_addr"), rvfiSIntWires("rs2_rdata"), rvfiUIntWires("rd_addr"),
            rvfiSIntWires("rd_wdata"), rvfiUIntWires("mem_addr"), rvfiSIntWires("mem_rdata"), rvfiSIntWires("mem_wdata")
        )
    }
}
