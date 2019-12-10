package se.emilsjolander.stickylistheaders;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ExpandableStickyListHeadersListView extends StickyListHeadersListView {
    public static final int ANIMATION_COLLAPSE = 1;
    public static final int ANIMATION_EXPAND = 0;
    IAnimationExecutor mDefaultAnimExecutor = new IAnimationExecutor() {
        public void executeAnim(View view, int i) {
            if (i == 0) {
                view.setVisibility(0);
            } else if (i == 1) {
                view.setVisibility(8);
            }
        }
    };
    ExpandableStickyListHeadersAdapter mExpandableStickyListHeadersAdapter;

    public interface IAnimationExecutor {
        void executeAnim(View view, int i);
    }

    public ExpandableStickyListHeadersListView(Context context) {
        super(context);
    }

    public ExpandableStickyListHeadersListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ExpandableStickyListHeadersListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ExpandableStickyListHeadersAdapter getAdapter() {
        return this.mExpandableStickyListHeadersAdapter;
    }

    public void setAdapter(StickyListHeadersAdapter stickyListHeadersAdapter) {
        this.mExpandableStickyListHeadersAdapter = new ExpandableStickyListHeadersAdapter(stickyListHeadersAdapter);
        super.setAdapter(this.mExpandableStickyListHeadersAdapter);
    }

    public View findViewByItemId(long j) {
        return this.mExpandableStickyListHeadersAdapter.findViewByItemId(j);
    }

    public long findItemIdByView(View view) {
        return this.mExpandableStickyListHeadersAdapter.findItemIdByView(view);
    }

    public void expand(long r2) {
        /* JADX: method processing error */
/*
Error: java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:410)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.fixIterableType(LoopRegionVisitor.java:308)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkIterableForEach(LoopRegionVisitor.java:249)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.processLoopRegion(LoopRegionVisitor.java:68)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.enterRegion(LoopRegionVisitor.java:52)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:56)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:18)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.visit(LoopRegionVisitor.java:46)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r1 = this;
        r0 = r1.mExpandableStickyListHeadersAdapter;
        r0 = r0.isHeaderCollapsed(r2);
        if (r0 != 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r0 = r1.mExpandableStickyListHeadersAdapter;
        r0.expand(r2);
        r0 = r1.mExpandableStickyListHeadersAdapter;
        r2 = r0.getItemViewsByHeaderId(r2);
        if (r2 != 0) goto L_0x0017;
    L_0x0016:
        return;
    L_0x0017:
        r2 = r2.iterator();
    L_0x001b:
        r3 = r2.hasNext();
        if (r3 == 0) goto L_0x002c;
    L_0x0021:
        r3 = r2.next();
        r3 = (android.view.View) r3;
        r0 = 0;
        r1.animateView(r3, r0);
        goto L_0x001b;
    L_0x002c:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView.expand(long):void");
    }

    public void collapse(long r2) {
        /* JADX: method processing error */
/*
Error: java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:410)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.fixIterableType(LoopRegionVisitor.java:308)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkIterableForEach(LoopRegionVisitor.java:249)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.processLoopRegion(LoopRegionVisitor.java:68)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.enterRegion(LoopRegionVisitor.java:52)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:56)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:18)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.visit(LoopRegionVisitor.java:46)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r1 = this;
        r0 = r1.mExpandableStickyListHeadersAdapter;
        r0 = r0.isHeaderCollapsed(r2);
        if (r0 == 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r0 = r1.mExpandableStickyListHeadersAdapter;
        r0.collapse(r2);
        r0 = r1.mExpandableStickyListHeadersAdapter;
        r2 = r0.getItemViewsByHeaderId(r2);
        if (r2 != 0) goto L_0x0017;
    L_0x0016:
        return;
    L_0x0017:
        r2 = r2.iterator();
    L_0x001b:
        r3 = r2.hasNext();
        if (r3 == 0) goto L_0x002c;
    L_0x0021:
        r3 = r2.next();
        r3 = (android.view.View) r3;
        r0 = 1;
        r1.animateView(r3, r0);
        goto L_0x001b;
    L_0x002c:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView.collapse(long):void");
    }

    public boolean isHeaderCollapsed(long j) {
        return this.mExpandableStickyListHeadersAdapter.isHeaderCollapsed(j);
    }

    public void setAnimExecutor(IAnimationExecutor iAnimationExecutor) {
        this.mDefaultAnimExecutor = iAnimationExecutor;
    }

    private void animateView(View view, int i) {
        if (i != 0 || view.getVisibility() != 0) {
            if ((1 != i || view.getVisibility() == 0) && this.mDefaultAnimExecutor != null) {
                this.mDefaultAnimExecutor.executeAnim(view, i);
            }
        }
    }
}
