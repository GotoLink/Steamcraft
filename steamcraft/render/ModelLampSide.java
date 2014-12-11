package steamcraft.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public final class ModelLampSide extends ModelBase {
	public ModelLampSide() {
		//constructor:
		Bracket2 = new ModelRenderer(this, 13, 16);
		Bracket2.addBox(-6.5F, 1F, -7.5F, 6, 2, 2);
		//Bracket2.setPosition(8F, -16F, -1F);
		Support = new ModelRenderer(this, 15, 8);
		Support.addBox(-7.5F, -3F, -8.5F, 1, 6, 4);
		//Support.setPosition(8F, -16F, -2F);
		CrossbarLeft = new ModelRenderer(this, 11, 11);
		CrossbarLeft.addBox(-7F, 1F, -7F, 2, 2, 7);
		//Bracket.setPosition(-1F, 0F, -1F);
		CrossbarRight = new ModelRenderer(this, 11, 11);
		CrossbarRight.addBox(-7F, 1F, -13.5F, 2, 2, 7);
		//Bracket.setPosition(-1F, 0F, -1F);
		Bracket = new ModelRenderer(this, 24, 3);
		Bracket.addBox(-1F, 0F, -7.5F, 2, 3, 2);
		//Bracket.setPosition(-1F, 0F, -1F);
		LowerLamp = new ModelRenderer(this, 40, 13);
		LowerLamp.addBox(-3F, -4F, -9.5F, 6, 4, 6);
		//LowerLamp.setPosition(-3F, 3F, -3F);
		UpperLamp = new ModelRenderer(this, 31, 0);
		UpperLamp.addBox(-4F, -9F, -10.5F, 8, 5, 8);
		//UpperLamp.setPosition(-4F, 7F, -4F);
		Top = new ModelRenderer(this, 0, 8);
		Top.addBox(-5F, -11F, -11.5F, 10, 2, 10);
		//Top.setPosition(-5F, 12F, -5F);
		TopPeak = new ModelRenderer(this, 0, 0);
		TopPeak.addBox(-3F, -13F, -9.5F, 6, 2, 6);
		//TopPeak.setPosition(-3F, 14F, -3F);
	}

	public void renderSign() {
		float f5 = 0.1F;
		Bracket.render(f5);
		Bracket2.render(f5);
		CrossbarLeft.render(f5);
		CrossbarRight.render(f5);
		LowerLamp.render(f5);
		Support.render(f5);
		Top.render(f5);
		TopPeak.render(f5);
		UpperLamp.render(f5);
	}

	public ModelRenderer Bracket;
	public ModelRenderer Bracket2;
	public ModelRenderer CrossbarLeft;
	public ModelRenderer CrossbarRight;
	public ModelRenderer LowerLamp;
	public ModelRenderer Support;
	public ModelRenderer Top;
	public ModelRenderer TopPeak;
	public ModelRenderer UpperLamp;
}