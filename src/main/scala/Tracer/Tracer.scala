package Tracer

import chisel3._

import Params._


class TracerIO(Params:Map[String,Int]) extends Bundle {
    // Inputs
    val rvfiUIntVec    : Vec[UInt] = Input(Vec(4, UInt(Params("XLEN").W)))
    val rvfiRegAddrVec : Vec[UInt] = Input(Vec(3, UInt(Params("RegAddrLen").W)))
    val rvfiSIntVec    : Vec[SInt] = Input(Vec(5, SInt(Params("XLEN").W)))
    val rvfiBoolVec    : Vec[Bool] = Input(Vec(1, Bool()))
    val rvfi_mode      : UInt      = Input(UInt(2.W))
}


class Tracer(Params:Map[String,Int]) extends Module {
    val io: TracerIO = IO(new TracerIO(Params))
    val clkCycle: UInt = RegInit(0.U(Params("XLEN").W))
    clkCycle := clkCycle + 1.U

    // Instruction Metadata
    // - rvfi_valid -> rvfiBoolVec(0)
    // - rvfi_insn  -> rvfiUIntVec(0)
    // - rvfi_mode  -> rvfi_mode

    // Register Read/Write
    // - rvfi_rd_addr   -> rvfiRegAddrVec(0)
    // - rvfi_rs1_addr  -> rvfiRegAddrVec(1)
    // - rvfi_rs2_addr  -> rvfiRegAddrVec(2)
    // - rvfi_rd_wdata  -> rvfiSIntVec(0)
    // - rvfi_rs1_rdata -> rvfiSIntVec(1)
    // - rvfi_rs2_rdata -> rvfiSIntVec(2)

    // Program Counter
    // - rvfi_pc_rdata -> rvfiUIntVec(1)
    // - rvfi_pc_wdata -> rvfiUIntVec(2)

    // Memory Access
    // - rvfi_mem_addr  -> rvfiUIntVec(3)
    // - rvfi_mem_rdata -> rvfiSIntVec(3)
    // - rvfi_mem_wdata -> rvfiSIntVec(4)

    when (io.rvfiBoolVec(0)) {
        printf(
            "ClkCycle: %d, pc_rdata: %x, pc_wdata: %x, insn: %x, mode: %d, rs1_addr: %d, rs1_rdata: %x, rs2_addr: %d, rs2_rdata: %x, rd_addr: %d, rd_wdata: %x, mem_addr: %x, mem_rdata: %x, mem_wdata: %x\n",
            clkCycle,             io.rvfiUIntVec(1), io.rvfiUIntVec(2),    io.rvfiUIntVec(0), io.rvfi_mode,
            io.rvfiRegAddrVec(1), io.rvfiSIntVec(1), io.rvfiRegAddrVec(2), io.rvfiSIntVec(2), io.rvfiRegAddrVec(0),
            io.rvfiSIntVec(0),    io.rvfiUIntVec(3), io.rvfiSIntVec(3),    io.rvfiSIntVec(4)
        )
    }
}
