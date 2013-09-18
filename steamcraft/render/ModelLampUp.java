package steamcraft.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelLampUp extends ModelBase
{
	public ModelLampUp()
    {
		Bracket = new ModelRenderer(this, 24, 3);
		Bracket.addBox(-1F, 0F, -7.5F, 2, 3, 2);
		//Bracket.setPosition(-1F, 0F, -1F);
		
		BracketWide = new ModelRenderer(this, 12, 13);
		BracketWide.addBox(-2F, 0F, -8.5F, 4, 3, 4);
		//Bracket.setPosition(-1F, 0F, -1F);
		
		CrossbarLeft = new ModelRenderer(this, 11, 16);
		CrossbarLeft.addBox(0F, 1F, -7.5F, 7, 2, 2);
		//Bracket.setPosition(-1F, 0F, -1F);
		
		CrossbarRight = new ModelRenderer(this, 11, 16);
		CrossbarRight.addBox(-7F, 1F, -7.5F, 7, 2, 2);
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

	public void renderSign()
    {
		Bracket.render(0.1F);
		BracketWide.render(0.1F);
		CrossbarLeft.render(0.1F);
		CrossbarRight.render(0.1F);
		LowerLamp.render(0.1F);
		Top.render(0.1F);
		TopPeak.render(0.1F);
		UpperLamp.render(0.1F);
    }

	public ModelRenderer Bracket;
	public ModelRenderer BracketWide;
	public ModelRenderer CrossbarLeft;
	public ModelRenderer CrossbarRight;
	public ModelRenderer LowerLamp;
	public ModelRenderer Top;
	public ModelRenderer TopPeak;
	public ModelRenderer UpperLamp;
}
