/*
 * The MIT License
 *
 * Copyright (c) 2009 The Broad Institute
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package picard.analysis.directed;

import picard.metrics.MultilevelMetrics;

/**
 * <p>Metrics generated by CollectHsMetrics for the analysis of target-capture sequencing experiments. The metrics
 * in this class fall broadly into three categories:</p>
 *
 * <ul>
 *     <li>Basic sequencing metrics that are either generated as a baseline against which to evaluate other
 *     metrics or because they are used in the calculation of other metrics.  This includes things like
 *     the genome size, the number of reads, the number of aligned reads etc.</li>
 *     <li>Metrics that are intended for evaluating the performance of the wet-lab assay that generated the data.
 *     This group includes metrics like the number of bases mapping on/off/near baits, %selected, fold 80 base
 *     penalty, hs library size and the hs penalty metrics. These metrics are calculated prior to some of the
 *     filters are applied (e.g. low mapping quality reads, low base quality bases and bases overlapping in the middle
 *     of paired-end reads are all counted).
 *     </li>
 *     <li>Metrics for assessing target coverage as a proxy for how well the data is likely to perform in downstream
 *     applications like variant calling. This group includes metrics like mean target coverage, the percentage of bases
 *     reaching various coverage levels, and the percentage of bases excluded by various filters. These metrics are computed
 *     using the strictest subset of the data, after all filters have been applied.</li>
 * </ul>
 *
 * @author Tim Fennell
 */
public class HsMetrics extends MultilevelMetrics {
    /** The name of the bait set used in the hybrid selection. */
    public String BAIT_SET;

    /** The number of bases in the reference genome used for alignment. */
    public long GENOME_SIZE;

    /** The number of bases which are localized to one or more baits. */
    public long BAIT_TERRITORY;

    /** The unique number of target bases in the experiment, where the target sequence is usually exons etc. */
    public long TARGET_TERRITORY;

    /** The ratio of TARGET_TERRITORY/BAIT_TERRITORY.  A value of 1 indicates a perfect design efficiency, while a valud of 0.5 indicates that half of bases within the bait region are not within the target region. */
    public double BAIT_DESIGN_EFFICIENCY;

    /** The total number of reads in the SAM or BAM file examined. */
    public long TOTAL_READS;

    /** The total number of reads that pass the vendor's filter. */
    public long PF_READS;

    /** The number of PF reads that are not marked as duplicates. */
    public long PF_UNIQUE_READS;

    /** The fraction of reads passing the vendor's filter, PF_READS/TOTAL_READS.   */
    public double PCT_PF_READS;

    /** The fraction of PF_UNIQUE_READS from the TOTAL_READS, PF_UNIQUE_READS/TOTAL_READS. */
    public double PCT_PF_UQ_READS;

    /** The number of PF_UNIQUE_READS that aligned to the reference genome with a mapping score > 0. */
    public long PF_UQ_READS_ALIGNED;

    /** The fraction of PF_UQ_READS_ALIGNED from the total number of PF reads. */
    public double PCT_PF_UQ_READS_ALIGNED;

    /** The number of PF unique bases that are aligned to the reference genome with mapping scores > 0. */
    public long PF_BASES_ALIGNED;

    /** The number of bases in the PF_UQ_READS_ALIGNED reads. Accounts for clipping and gaps. */
    public long PF_UQ_BASES_ALIGNED;

    /** The number of PF_BASES_ALIGNED that are mapped to the baited regions of the genome. */
    public long ON_BAIT_BASES;

    /** The number of PF_BASES_ALIGNED that are mapped to within a fixed interval containing a baited region, but not within the baited section per se. */
    public long NEAR_BAIT_BASES;

    /** The number of PF_BASES_ALIGNED that are mapped away from any baited region. */
    public long OFF_BAIT_BASES;

    /** The number of PF_BASES_ALIGNED that are mapped to a targeted region of the genome. */
    public long ON_TARGET_BASES;

    /** The fraction of PF_BASES_ALIGNED located on or near a baited region (ON_BAIT_BASES + NEAR_BAIT_BASES)/PF_BASES_ALIGNED. */
    public double PCT_SELECTED_BASES;

    /** The fraction of PF_BASES_ALIGNED that are mapped away from any baited region, OFF_BAIT_BASES/PF_BASES_ALIGNED.  */
    public double PCT_OFF_BAIT;

    /** The fraction of bases on or near baits that are covered by baits, ON_BAIT_BASES/(ON_BAIT_BASES + NEAR_BAIT_BASES). */
    public double ON_BAIT_VS_SELECTED;

    /** The mean coverage of all baits in the experiment. */
    public double MEAN_BAIT_COVERAGE;

    /** The mean coverage of a target region. */
    public double MEAN_TARGET_COVERAGE;

    /** The median coverage of a target region. */
    public double MEDIAN_TARGET_COVERAGE;

    /** The maximum coverage of reads that mapped to target regions of an experiment. */
    public long MAX_TARGET_COVERAGE;

    /** The number of aligned, de-duped, on-bait bases out of the PF bases available. */
    public double PCT_USABLE_BASES_ON_BAIT;

    /** The number of aligned, de-duped, on-target bases out of all of the PF bases available. */
    public double PCT_USABLE_BASES_ON_TARGET;

    /** The fold by which the baited region has been amplified above genomic background. */
    public double FOLD_ENRICHMENT;

    /** The fraction of targets that did not reach coverage=1 over any base. */
    public double ZERO_CVG_TARGETS_PCT;

    /** The fraction of aligned bases that were filtered out because they were in reads marked as duplicates. */
    public double PCT_EXC_DUPE;

    /** The fraction of aligned bases that were filtered out because they were in reads with low mapping quality. */
    public double PCT_EXC_MAPQ;

    /** The fraction of aligned bases that were filtered out because they were of low base quality. */
    public double PCT_EXC_BASEQ;

    /** The fraction of aligned bases that were filtered out because they were the second observation from an insert with overlapping reads. */
    public double PCT_EXC_OVERLAP;

    /** The fraction of aligned bases that were filtered out because they did not align over a target base. */
    public double PCT_EXC_OFF_TARGET;

    /** The fold over-coverage necessary to raise 80% of bases in "non-zero-cvg" targets to the mean coverage level in those targets.*/
    public double FOLD_80_BASE_PENALTY;

    /** The fraction of all target bases achieving 1X or greater coverage. */
    public double PCT_TARGET_BASES_1X;
    /** The fraction of all target bases achieving 2X or greater coverage. */
    public double PCT_TARGET_BASES_2X;
    /** The fraction of all target bases achieving 10X or greater coverage. */
    public double PCT_TARGET_BASES_10X;
    /** The fraction of all target bases achieving 20X or greater coverage. */
    public double PCT_TARGET_BASES_20X;
	/** The fraction of all target bases achieving 30X or greater coverage. */
	public double PCT_TARGET_BASES_30X;
	/** The fraction of all target bases achieving 40X or greater coverage. */
	public double PCT_TARGET_BASES_40X;
	/** The fraction of all target bases achieving 50X or greater coverage. */
	public double PCT_TARGET_BASES_50X;
	/** The fraction of all target bases achieving 100X or greater coverage. */
	public double PCT_TARGET_BASES_100X;
        public double PCT_TARGET_BASES_150X;
        public double PCT_TARGET_BASES_200X;
        public double PCT_TARGET_BASES_250X;
        public double PCT_TARGET_BASES_300X;
        public double PCT_TARGET_BASES_350X;
        public double PCT_TARGET_BASES_400X;
        public double PCT_TARGET_BASES_450X;
        public double PCT_TARGET_BASES_500X;
        public double PCT_TARGET_BASES_550X;
        public double PCT_TARGET_BASES_600X;
        public double PCT_TARGET_BASES_650X;
        public double PCT_TARGET_BASES_700X;
        public double PCT_TARGET_BASES_750X;
        public double PCT_TARGET_BASES_800X;
        public double PCT_TARGET_BASES_850X;
        public double PCT_TARGET_BASES_900X;
        public double PCT_TARGET_BASES_950X;
        public double PCT_TARGET_BASES_1000X;

    /** The estimated number of unique molecules in the selected part of the library. */
    public Long HS_LIBRARY_SIZE;

    /**
     * The "hybrid selection penalty" incurred to get 80% of target bases to 10X. This metric
     * should be interpreted as: if I have a design with 10 megabases of target, and want to get
     * 10X coverage I need to sequence until PF_ALIGNED_BASES = 10^7 * 10 * HS_PENALTY_10X.
     */
    public double HS_PENALTY_10X;

    /**
     * The "hybrid selection penalty" incurred to get 80% of target bases to 20X. This metric
     * should be interpreted as: if I have a design with 10 megabases of target, and want to get
     * 20X coverage I need to sequence until PF_ALIGNED_BASES = 10^7 * 20 * HS_PENALTY_20X.
     */
    public double HS_PENALTY_20X;

	/**
	 * The "hybrid selection penalty" incurred to get 80% of target bases to 30X. This metric
	 * should be interpreted as: if I have a design with 10 megabases of target, and want to get
	 * 30X coverage I need to sequence until PF_ALIGNED_BASES = 10^7 * 30 * HS_PENALTY_30X.
	 */
	public double HS_PENALTY_30X;

	/**
	 * The "hybrid selection penalty" incurred to get 80% of target bases to 40X. This metric
	 * should be interpreted as: if I have a design with 10 megabases of target, and want to get
	 * 40X coverage I need to sequence until PF_ALIGNED_BASES = 10^7 * 40 * HS_PENALTY_40X.
	 */
	public double HS_PENALTY_40X;

	/**
	 * The "hybrid selection penalty" incurred to get 80% of target bases to 50X. This metric
	 * should be interpreted as: if I have a design with 10 megabases of target, and want to get
	 * 50X coverage I need to sequence until PF_ALIGNED_BASES = 10^7 * 50 * HS_PENALTY_50X.
	 */
	public double HS_PENALTY_50X;

	/**
	 * The "hybrid selection penalty" incurred to get 80% of target bases to 100X. This metric
	 * should be interpreted as: if I have a design with 10 megabases of target, and want to get
	 * 100X coverage I need to sequence until PF_ALIGNED_BASES = 10^7 * 100 * HS_PENALTY_100X.
	 */
	public double HS_PENALTY_100X;

    /**
     * A measure of how undercovered <= 50% GC regions are relative to the mean. For each GC bin [0..50]
     * we calculate a = % of target territory, and b = % of aligned reads aligned to these targets.
     * AT DROPOUT is then abs(sum(a-b when a-b < 0)). E.g. if the value is 5% this implies that 5% of total
     * reads that should have mapped to GC<=50% regions mapped elsewhere.
     */
    public double AT_DROPOUT;

    /**
     * A measure of how undercovered >= 50% GC regions are relative to the mean. For each GC bin [50..100]
     * we calculate a = % of target territory, and b = % of aligned reads aligned to these targets.
     * GC DROPOUT is then abs(sum(a-b when a-b < 0)). E.g. if the value is 5% this implies that 5% of total
     * reads that should have mapped to GC>=50% regions mapped elsewhere.
     */
    public double GC_DROPOUT;

    /** The theoretical HET SNP sensitivity. */
    public double HET_SNP_SENSITIVITY;

    /** The Phred Scaled Q Score of the theoretical HET SNP sensitivity. */
    public double HET_SNP_Q;
}
